import java.io.File
import scala.io.Source
import scala.util.Success
import java.io.FileNotFoundException
import java.io.IOException
import scala.util.Failure

// so much can go wrong here
// - the file might not be there
// - there could be a problem with permissions of the file
// - the file contents might be not in valid encoding
// but this is not shown in the signature but it probably should
def readFile(path: String): String =
  val source = Source.fromFile(path)
  try source.getLines.mkString("\n")
  finally source.close()

// this is better but we still dont get the whole infromation
// about the type of the error that might occur
def readFileTry(path: String): scala.util.Try[String] =
  scala.util.Try(readFile(path))

enum MyError:
  case FileAccessProblems(ex: FileNotFoundException)
  case IOexception(ex: IOException)

// only conventions dictate that right is a success case and left is an error
def readFileEither(path: String): Either[MyError, String] =
  readFileTry(path) match
    case Success(r) => Right(r)
    case Failure(ex: FileNotFoundException) =>
      Left(MyError.FileAccessProblems(ex))
    case Failure(ex: IOException) => Left(MyError.IOexception(ex))
    case _                        => ???

@main def errors(): Unit =
//❯ touch /tmp/untouchable.txt
//❯ chmod 000 /tmp/untouchable.txt
  println(readFileEither("/tmp/untouchable.txt"))
//❯ echo -ne "\xa0\xa1" > /tmp/invalid.txt
  println(readFileEither("/tmp/invalid.txt"))

// ❯ echo "hello" > /tmp/a.txt
// ❯ echo " world" > /tmp/b.txt
// we can easily chain eithers using for comprehension
// this will simply buble the error
  val msg: Either[MyError, String] = for {
    a <- readFileEither("/tmp/a.txt")
    b <- readFileEither("/tmp/b.txt")
  } yield s"$a$b"

  println(msg)
