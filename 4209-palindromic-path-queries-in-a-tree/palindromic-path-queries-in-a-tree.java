
import java.util.*;

class Solution {
    int n, LOG, timer;
    List<Integer>[] adj;
    int[] tin, tout, depth, bit;
    int[][] up;

    void updateBIT(int i, int val) {
        while (i <= n + 1) {
            bit[i] ^= val;
            i += i & -i;
        }
    }

    void rangeUpdate(int l, int r, int val) {
        updateBIT(l, val);
        updateBIT(r + 1, val);
    }

    int prefixXOR(int i) {
        int result = 0;
        while (i > 0) {
            result ^= bit[i];
            i -= i & -i;
        }
        return result;
    }

    void dfs(int u, int parent) {
        tin[u] = ++timer;
        up[u][0] = parent;

        for (int j = 1; j < LOG; j++) {
            up[u][j] = up[up[u][j - 1]][j - 1];
        }

        for (int v : adj[u]) {
            if (v == parent) continue;
            depth[v] = depth[u] + 1;
            dfs(v, u);
        }

        tout[u] = timer;
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < LOG; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[u][j];
            }
        }

        if (u == v) return u;

        for (int j = LOG - 1; j >= 0; j--) {
            if (up[u][j] != up[v][j]) {
                u = up[u][j];
                v = up[v][j];
            }
        }

        return up[u][0];
    }

    public List<Boolean> palindromePath(
            int n, int[][] edges, String s, String[] queries) {

        this.n = n;
        LOG = 1;
        while ((1 << LOG) <= n) LOG++;

        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adj[i] = new ArrayList<>();
        }

        for (int[] e : edges) {
            adj[e[0]].add(e[1]);
            adj[e[1]].add(e[0]);
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[n][LOG];
        bit = new int[n + 2];

        dfs(0, 0);

        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            rangeUpdate(tin[i], tout[i], mask);
        }

        List<Boolean> results = new ArrayList<>();

        for (String q : queries) {
            String[] parts = q.trim().split("\\s+");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                int oldMask = 1 << (chars[u] - 'a');
                int newMask = 1 << (c - 'a');

                if (oldMask != newMask) {
                    rangeUpdate(tin[u], tout[u],
                                oldMask ^ newMask);
                    chars[u] = c;
                }

            } else if (parts[0].equals("query")) {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int maskU = prefixXOR(tin[u]);
                int maskV = prefixXOR(tin[v]);
                int ancestorMask =
                        1 << (chars[ancestor] - 'a');

                int pathMask = maskU ^ maskV ^ ancestorMask;

                results.add(
                    (pathMask & (pathMask - 1)) == 0
                );
            }
        }

        return results;
    }
}
