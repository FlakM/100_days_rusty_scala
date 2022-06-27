// this code would panic (unrecoverable problem)
// due to calling unwrap on it
// the std::fs::read_to_string has a signature of
// `Result<T, Error>`
fn read_file_naive(path: String) -> String {
    std::fs::read_to_string(path).unwrap()
}

fn read_file(path: String) -> Result<String, std::io::Error> {
    std::fs::read_to_string(path)
}

// this function showcases the question mark operator that might be used to bubble up the errors
// to a caller - in general more often than not he is the one that is able to decide what to do
// with an error
fn _read_file_with_operator(path: String) -> Result<String, std::io::Error> {
    let content = std::fs::read_to_string(path)?;
    Ok(content) 
}

fn main() {
    read_file_naive("/tmp/aa.txt".to_owned());

    // how to use either case of the result
    // notice that I didn't introduce any custom types
    // the std::fs::read_to_string function returns the correct types by itself
    match read_file("/tmp/a.txt".to_owned()) {
        Ok(file_content) => println!("{file_content}"),
        Err(err) => match err.kind() {
            // checkout the amount of exceptions in this package!
            std::io::ErrorKind::NotFound => println!("the file was not found"),
            _ => println!("other kind of error {err}"),
        },
    }
}
