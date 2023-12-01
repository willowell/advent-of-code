import harness.{
  Day,
  Year,
  createDayFiles,
  dayOpt,
  yearOpt,
}

import cats.implicits.*
import com.monovore.decline.*

object create extends CommandApp(
  name = "create",
  header = "",
  main = (yearOpt, dayOpt).mapN { (year, day) =>
    createDayFiles(year, day)
  }
)
