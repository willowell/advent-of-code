import harness.*

import cats.implicits.*
import com.monovore.decline.*

object download extends CommandApp(
  name = "download",
  header = "",
  main = (yearOpt, dayOpt).mapN { (year, day) =>
    Aocd.downloadToInputFile(year, day)
  }
)
