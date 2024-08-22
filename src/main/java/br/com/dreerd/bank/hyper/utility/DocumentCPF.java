package br.com.dreerd.bank.hyper.utility;

import br.com.dreerd.bank.hyper.exception.DocumentException;
import lombok.extern.log4j.Log4j2;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public final class DocumentCPF {
    private static final Pattern CPF_PATTERN = Pattern.compile("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}");

    private static final Set<String> BLACKLIST = generateBlacklist();

    public static boolean isValidCPF(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return false;
        }

        try {
            String cleanedCpf = cleanCPF(cpf);

            if (BLACKLIST.contains(cleanedCpf)) {
                return false;
            }

            if (cleanedCpf.length() != 11) {
                return false;
            }

            return isValidDigits(cleanedCpf);

        } catch (Exception e) {
            throw new DocumentException(cpf, "CPF", "Error validating CPF: " + e.getMessage(), e);
        }
    }

    private static String cleanCPF(String cpf) {
        return cpf.replaceAll("\\D", ""); // Remove todos os caracteres não numéricos
    }

    private static boolean isValidDigits(String cpf) {
        int firstDigit = calculateDigit(cpf, 9);
        int secondDigit = calculateDigit(cpf, 10);

        String calculatedDigits = String.format("%d%d", firstDigit, secondDigit);
        String cpfDigits = cpf.substring(9);

        return calculatedDigits.equals(cpfDigits);
    }

    private static int calculateDigit(String cpf, int length) {
        int sum = 0;
        int multiplier = length + 1;

        for (int i = 0; i < length; i++) {
            sum += (cpf.charAt(i) - '0') * multiplier--;
        }

        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static Set<String> generateBlacklist() {
        return IntStream.rangeClosed(0, 9)
                .mapToObj(i -> String.format("%03d%03d%03d%02d", i, i, i, i))
                .collect(Collectors.toSet());
    }
}
