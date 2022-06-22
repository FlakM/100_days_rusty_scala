trait SymmetricEncryptor:
  type KeyLen <: Int

  // todo figure out how the key length could be type safe
  type Key = Array[Byte]

  def encrypt(key: Key, body: Array[Byte]) : Array[Byte] 



@main def run(): Unit =
  println("hello world!")

