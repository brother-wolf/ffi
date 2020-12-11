#[no_mangle]
pub extern fn sum_of_fibonacci_unsafe_unwrap(input: usize) -> usize {
    fn rec(idx: &mut Vec<usize>, input: usize) -> &Vec<usize> {
        if idx.len() >= input {
             idx
        } else {
            let i = idx.len();
            let new: usize = idx.get(i - 1).unwrap() + idx.get(i - 2).unwrap();
            idx.append(&mut vec![new]);
            rec(idx, input)
        }
    }
    let mut idx = vec![1usize, 2];

    rec(&mut idx, input).iter().sum()
}

#[no_mangle]
pub extern fn sum_of_fibonacci(input: usize) -> usize {
    fn rec(idx: &mut Vec<usize>, input: usize) -> &Vec<usize> {
        if idx.len() >= input {
            idx
        } else {
            let i = idx.len();
            let new: usize = match (idx.get(i - 1), idx.get(i - 2)) {
                (Some(a), Some(b)) => a + b,
                _ => 0,
            };
            idx.append(&mut vec![new]);
            rec(idx, input)
        }
    }
    let mut idx = vec![1usize, 2];

    rec(&mut idx, input).iter().sum()
}

#[cfg(test)]
fn run_assertions(label: &str, f: extern fn(usize) -> usize) {
    use std::time::SystemTime;

    let cycles = (0..201).map(|_a| {
        let start = SystemTime::now();
        assert_eq!(f(3), 6);
        assert_eq!(f(5), 19);
        assert_eq!(f(10), 231);
        assert_eq!(f(13), 985);
        assert_eq!(f(43), 1836311901); // Scala's Int limit is around here
        // assert_eq!(f(50), 53316291171);
        // assert_eq!(f(90), 12200160415121876736);
        SystemTime::now().duration_since(start).unwrap().as_micros() as usize
    }).collect::<Vec<usize>>();
    let sum: usize = cycles.iter().sum();
    let count = cycles.len();
    println!("{}: sum:{}: average:{}: of:{}: ...  {:?}", label, sum, sum / count, count, cycles);
}

#[cfg(test)]
fn time_it(func: extern fn(usize) -> usize, number: usize) -> usize {
    use std::time::SystemTime;
    let start = SystemTime::now();
    func(number);
    SystemTime::now().duration_since(start).unwrap().as_micros() as usize
}


#[cfg(test)]
fn process(label: &str, func: extern fn(usize) -> usize) {
    let sequence = load_sequence();
    let cycles = sequence.into_iter().map(|number| {
        time_it(func, number)
    }).collect::<Vec<usize>>();
    let sum: usize = cycles.iter().sum();
    let count = cycles.len();
    println!("{}: sum:{}: average:{}: of:{}: ...  {:?}", label, sum, sum / count, count, cycles);
}


#[cfg(test)]
fn load_sequence() -> Vec<usize> {
    vec![16, 42, 23, 12, 9, 31, 21, 19, 7, 21, 9, 21, 16, 35, 12, 33, 16, 35, 35, 17, 25, 20, 27, 26, 3, 10, 12, 2, 3, 30, 22, 19, 11, 42, 11, 1, 43, 6, 37, 28, 14, 10, 15, 13, 10, 32, 16, 36, 33, 28, 27, 34, 25, 31, 29, 36, 4, 10, 33, 33, 21, 19, 28, 34, 18, 37, 3, 12, 2, 14, 6, 24, 14, 38, 8, 24, 43, 33, 4, 11, 32, 3, 15, 34, 18, 25, 1, 2, 16, 7, 21, 34, 39, 19, 15, 6, 17, 2, 23, 24, 4, 25, 37, 26, 37, 27, 25, 24, 2, 22, 13, 37, 39, 16, 43, 25, 25, 22, 11, 23, 19, 22, 24, 40, 35, 12, 19, 5, 23, 12, 1, 38, 21, 36, 1, 36, 39, 18, 39, 21, 19, 33, 34, 16, 35, 13, 21, 29, 5, 9, 20, 25, 5, 41, 41, 4, 6, 13, 40, 14, 2, 4, 17, 24, 25, 37, 28, 36, 14, 38, 14, 2, 39, 32, 16, 18, 20, 43, 18, 38, 8, 36, 34, 23, 14, 12, 38, 17, 9, 38, 8, 10, 22, 40, 2, 8, 24, 26, 22, 31, 40]
}

#[ignore]
#[test]
fn original_assertions_fibonacci_test() {
    run_assertions("Fibonacci match (orig assertions)", sum_of_fibonacci);
}

#[ignore]
#[test]
fn original_assertions_fibonacci_unsafe_unwrap_test() {
    run_assertions("Fibonacci unsafe unwrap (orig assertions)", sum_of_fibonacci_unsafe_unwrap);
}


#[test]
fn fibonacci_test() {
    process("Fibonacci match", sum_of_fibonacci);
}

#[test]
fn fibonacci_unsafe_unwrap_test() {
    process("Fibonacci unsafe unwrap", sum_of_fibonacci_unsafe_unwrap);
}
