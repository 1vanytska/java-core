# Movie Statistics Parser

A console Java application that parses JSON files containing movie data and 
generates statistics based on a selected attribute. The program supports 
parallel file processing using a thread pool and outputs results in XML format.

---

## Domain model

**Main entity — `Movie`**

Attributes:

* `title` — movie title (String)
* `director` — director name (String)
* `year` — release year (Integer)
* `genre` — array of genres (multi-value attribute)

Relation: many `Movie` → one `Director` (logical relation; `director` stored as a string).

---

# Project Structure

```
com.sofiia
├── model              # Data models
│   ├── Movie          # Main entity representing a movie
│   └── StatisticItem  # Single statistic item (value + count)
│
├── parser             # JSON file parsing
│   ├── JsonParserUtil # Jackson configuration and stream parser
│   └── MovieService   # Reads all JSON files in separate threads using a thread pool
│
├── statistics         # Statistics calculation logic
│   ├── StatisticsCalculator # Counts occurrences of values by attribute
│   └── StatisticsWrapper    # Wrapper for proper XML formatting
│
├── output             # Output handling
│   └── XmlWriter      # Generates XML file statistics_by_{attribute}.xml
│
├── ui                 # Console interaction
│   └── ConsoleInterface # Allows selecting attribute (director, year, genre)
│
└── Main               # Main entry: loads movies, calculates statistics, generates XML
```

---

## Input format

One or more JSON files inside a directory. Each file contains an array of movie objects:

```json
[
  {
    "title": "Inception",
    "year": 2010,
    "director": "Christopher Nolan",
    "genre": ["Sci-Fi", "Thriller"]
  },
  {
    "title": "Titanic",
    "year": 1997,
    "director": "James Cameron",
    "genre": ["Drama", "Romance"]
  }
]
```

Supported statistic attributes:

* `director`
* `year`
* `genre` (split by comma, trimmed)

---

## Output format

XML file named `statistics_by_{attribute}.xml`. Example `statistics_by_year.xml`:

```xml
<StatisticsWrapper>
  <statistics>
    <item>
      <value>2003</value>
      <count>3</count>
    </item>
    <item>
      <value>2008</value>
      <count>1</count>
    </item>
  </statistics>
</StatisticsWrapper>
```

Items are sorted by `count` descending.

---

## Thread Pool Performance Test

11 files were used:

- movies_0.json
- movies_1.json
- movies_2.json
- ...
- movies_10.json

Each file contains 100 000 movie objects (based on the dataset above).

### Performance Table

| Runs / Threads | 1 Thread | 2 Threads | 4 Threads | 8 Threads |
|----------------|----------|-----------|-----------|-----------|
| Run #1         | 733 ms   | 595 ms    | 536 ms    | 444 ms    |
| Run #2         | 897 ms   | 663 ms    | 541 ms    | 506 ms    |
| Run #3         | 816 ms   | 578 ms    | 552 ms    | 500 ms    |

### Observations

- All three runs (each using a different attribute) demonstrate a **consistent speed-up** when increasing the thread count.
- Moving from **1 → 2 threads** provides a noticeable improvement (15–30% faster).
- Moving from **2 → 4 threads** still increases performance, stabilizing around ~540 ms.
- Moving from **4 → 8 threads** gives only a minor additional improvement because:
  - parsing large files is **I/O bound**, not CPU bound;
  - overhead of managing 8 threads starts to reduce efficiency gain.

---

### Conclusions

- Higher thread counts help, but gains shrink after 4 threads.
- For datasets over **1 million JSON records**, the **optimal thread count is 4**.
- Increasing to **8 threads** is still beneficial, but efficiency per thread drops.
