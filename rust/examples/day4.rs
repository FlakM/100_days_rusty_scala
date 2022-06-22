struct Algo<const N: usize> {
    key: [u8; N],
}

impl Algo<1024> {
    fn encrypt(&self, msg: &[u8]) -> Vec<u8> {
        unimplemented!()
    }
}

fn main() {
    // if you use some value not equal to 1024 this example will fail
    // since there are no other implementations for Algo which is 
    // parametrized by length of the key
    let a = Algo { key:  [b'a'; 1024] };
    let msg = "hello world!".as_bytes();
    println!("{:?}", a.encrypt(msg))
}
