#![allow(dead_code)]

#[derive(Debug, PartialEq)]
struct PersonSimple {
    simple_name: String,
}

#[derive(Clone)]
struct PersonWithVocation {
    name: String,
    vocation: String,
}

impl From<PersonWithVocation> for PersonSimple {
    fn from(p: PersonWithVocation) -> Self {
        PersonSimple {
            simple_name: p.name,
        }
    }
}

fn main() {
    let me = PersonWithVocation {
        name: "Maciek".to_owned(),
        vocation: "coder".to_owned(),
    };

    let simple_me = PersonSimple::from(me.clone());

    // we have to help rust a little bit and give a type suggestion here
    let simple_me2: PersonSimple = me.into();

    assert_eq!(simple_me, simple_me2);
}
