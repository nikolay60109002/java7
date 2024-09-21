import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.FileHandler;
import java.io.IOException;

// Интерфейс для комплексного числа 
interface ComplexOperation {
    ComplexNumber execute(ComplexNumber a, ComplexNumber b);
}

// Класс комплексного числа
class ComplexNumber {
    double real;
    double imaginary;

    public ComplexNumber(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    @Override
    public String toString() {
        return real + (imaginary >= 0 ? "+" : "") + imaginary + "i";
    }
}

// Класс сложения 
class ComplexAddition implements ComplexOperation {
    @Override
    public ComplexNumber execute(ComplexNumber a, ComplexNumber b) {
        return new ComplexNumber(a.real + b.real, a.imaginary + b.imaginary);
    }
}

// Класс умножения 
class ComplexMultiplication implements ComplexOperation {
    @Override
    public ComplexNumber execute(ComplexNumber a, ComplexNumber b) {
        double real = a.real * b.real - a.imaginary * b.imaginary;
        double imaginary = a.real * b.imaginary + a.imaginary * b.real;
        return new ComplexNumber(real, imaginary);
    }
}

// Класс деления 
class ComplexDivision implements ComplexOperation {
    @Override
    public ComplexNumber execute(ComplexNumber a, ComplexNumber b) {
        double denominator = b.real * b.real + b.imaginary * b.imaginary;
        if (denominator == 0) {
            throw new ArithmeticException("Division by zero");
        }
        double real = (a.real * b.real + a.imaginary * b.imaginary) / denominator;
        double imaginary = (a.imaginary * b.real - a.real * b.imaginary) / denominator;
        return new ComplexNumber(real, imaginary);
    }
}

// Калькулятор 
public class ComplexCalculator {
    private static final Logger logger = Logger.getLogger(ComplexCalculator.class.getName());

    static {
        try {
            FileHandler fileHandler = new FileHandler("calculator.log");
            SimpleFormatter formatter = new SimpleFormatter();
            fileHandler.setFormatter(formatter);
            logger.addHandler(fileHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ComplexNumber calculate(ComplexNumber a, ComplexNumber b, ComplexOperation operation) {
        logger.info("Calculating: " + a + " " + operation.getClass().getSimpleName() + " " + b);
        ComplexNumber result = operation.execute(a, b);
        logger.info("Result: " + result);
        return result;
    }

    public static void main(String[] args) {
        ComplexCalculator calculator = new ComplexCalculator();

        ComplexNumber a = new ComplexNumber(2, 3);
        ComplexNumber b = new ComplexNumber(1, -1);

        ComplexNumber sum = calculator.calculate(a, b, new ComplexAddition());
        System.out.println("Сумма: " + sum);

        ComplexNumber product = calculator.calculate(a, b, new ComplexMultiplication());
        System.out.println("Произведение: " + product);

        ComplexNumber quotient = calculator.calculate(a, b, new ComplexDivision());
        System.out.println("Частное: " + quotient);
    }
}