enum Car {
    FastCar { speed: usize },
    _OldCar { age: u8 },
}

fn show_car(car: &Car) {
    match car {
        Car::FastCar { speed } => println!("my fast car can ride up to {}", speed),
        // this results in warning only in scala
        _ => unimplemented!(),
    }
}
fn main() {
    let my_car = Car::FastCar { speed: 100 };
    show_car(&my_car);
    let index = std::mem::discriminant(&my_car);
    // this will print:
    // my fast car has index of Discriminant(0)
    println!("my fast car has index of {index:?}")
}
