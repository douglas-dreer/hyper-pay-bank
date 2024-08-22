package br.com.dreerd.bank.hyper.utility;

import br.com.dreerd.bank.hyper.exception.DocumentException;
import lombok.extern.log4j.Log4j2;

import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Log4j2
public class DocumentCNPJ {
    private static final Pattern CNPJ_PATTERN = Pattern.compile("\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}");

    private static final Set<String> BLACKLIST = generateBlacklist();

    public static boolean isValidCNPJ(String cnpj) {
        if (cnpj == null || cnpj.isBlank()) {
            return false;
        }

        try {
            String cleanedCnpj = cleanCNPJ(cnpj);

            if (BLACKLIST.contains(cleanedCnpj)) {
                return false;
            }

            if (cleanedCnpj.length() != 14) {
                return false;
            }

            return isValidDigits(cleanedCnpj);

        } catch (Exception e) {
            throw new DocumentException(cnpj, "CNPJ", "Error validating CNPJ: " + e.getMessage(), e);
        }
    }

    private static String cleanCNPJ(String cnpj) {
        return cnpj.replaceAll("\\D", "");
    }

    private static boolean isValidDigits(String cnpj) {
        int firstDigit = calculateDigit(cnpj, 12);
        int secondDigit = calculateDigit(cnpj, 13);

        return cnpj.charAt(12) == (char) (firstDigit + '0') && cnpj.charAt(13) == (char) (secondDigit + '0');
    }

    private static int calculateDigit(String cnpj, int length) {
        int[] weights = length == 12 ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2} : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;

        for (int i = 0; i < length; i++) {
            sum += (cnpj.charAt(i) - '0') * weights[i];
        }

        int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static Set<String> generateBlacklist() {
        return IntStream.rangeClosed(0, 9)
                .mapToObj(i -> String.format("%02d%03d%03d0001%02d", i, i, i, i))
                .collect(Collectors.toSet());
    }
}
