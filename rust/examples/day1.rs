/// this acts as struct from foreign crate
/// therefore we cannot implement trait for the `Person`
struct Person {
    pub name: String,
}

pub trait PersonExt {
    fn hello(&self);
}

// implementing local traits for foreign types is ok ;)
impl PersonExt for Person {
    fn hello(&self) {
        println!("hello {}", self.name);
    }
}

fn main() {
    let p = Person {
        name: "Maciek".to_owned(),
    };
    p.hello();
}
