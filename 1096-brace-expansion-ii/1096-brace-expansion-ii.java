import java.util.*;

class Solution {
    public List<String> braceExpansionII(String expression) {
        Set<String> result = parse(expression, new int[]{0});
        List<String> ans = new ArrayList<>(result);
        Collections.sort(ans);
        return ans;
    }

    private Set<String> parse(String s, int[] i) {
        Set<String> result = new HashSet<>();
        Set<String> curr = new HashSet<>();
        curr.add("");

        while (i[0] < s.length() && s.charAt(i[0]) != '}'
                && s.charAt(i[0]) != ',') {

            Set<String> next;

            if (s.charAt(i[0]) == '{') {
                i[0]++;
                next = parse(s, i);
                i[0]++; // skip '}'
            } else {
                next = new HashSet<>();
                next.add(String.valueOf(s.charAt(i[0]++)));
            }

            curr = concatenate(curr, next);
        }

        result.addAll(curr);

        while (i[0] < s.length() && s.charAt(i[0]) == ',') {
            i[0]++; // skip ','

            Set<String> next = parse(s, i);
            result.addAll(next);
        }

        return result;
    }

    private Set<String> concatenate(Set<String> a, Set<String> b) {
        Set<String> result = new HashSet<>();

        for (String x : a) {
            for (String y : b) {
                result.add(x + y);
            }
        }

        return result;
    }
}