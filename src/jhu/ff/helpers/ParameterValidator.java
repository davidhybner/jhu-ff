package jhu.ff.helpers;

public class ParameterValidator {
    public static boolean validateParameters(String ... params) {
        for(String param : params) {
            if(param == null)
                return false;
        }

        return true;
    }
}
