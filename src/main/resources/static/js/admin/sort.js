var sortTable = function (n) {
    var s = n.document;
    return function (b, t, u, d, n, x) {
        function p(e, b, d) {
            function m(a, g, b) {
                var c;
                if ("string" === g) return a;
                "[object RegExp]" === {}.toString.call(g) ? c = g : "undefined" !== typeof v[g] && (c = v[g]);
                b = b ? b : 0;
                return (c = a.match(c)) && c[b] ? "word" == g ? c[b] : new Number(c[b]) : a
            }

            function f(a, b) {
                return a > b
            }

            function p(a, b) {
                return a < b
            }

            function w(b, d, f) {
                if (q.dir === d && q.idx === e) return !1;
                for (a = 0; a < h.length - 1; a += 1) for (k = a + 1; k < h.length; k += 1) if (b(m(h[a].cells[e].innerText, l[e][0], l[e][1]), m(h[k].cells[e].innerText, l[e][0], l[e][1]))) {
                    var c = h[a], r = h[k], n = c.nextSibling, p = c.parentNode;
                    c.swapNode ? c.swapNode(r) : (r.parentNode.replaceChild(c, r), p.insertBefore(r, n))
                }
                q.dir = d;
                q.idx = e;
                f && f(q)
            }

            b = s.createElement("SPAN");
            d = s.createElement("SPAN");
            b.appendChild(t.cloneNode(!0));
            d.appendChild(u.cloneNode(!0));
            b.onclick = function () {
                w(p, "asc", n)
            };
            d.onclick = function () {
                w(f, "desc", x)
            };
            return {asc: b, desc: d}
        }

        if (!b || !t || !u) throw Error("sortTabe ERROR: Parameters 1,2,3 - is required!");
        var h = b.tBodies[0].rows, f = b.rows[0].cells.length, q = {dir: "none", idx: null}, l = d || [],
            v = {integer: /\d+/g, number: /(\d+(\.\d+)?)/g, word: /\S+/g, string: "string"}, m, a, k;
        for (a = 0; a < f; a++) "[object Array]" !== {}.toString.call(l[a]) && (l[a] = ["string", 0]);
        for (a = 0; a < f; a += 1) m = p(a, b), d = b.rows[0].cells[a], d.appendChild(m.asc), d.appendChild(m.desc)
    }
}(this);

$(document).ready(function () {
    $("body").on('click', 'a', function () {

        if ($(this).attr('id') === "filter") {
            var param = "d1=" + $("#datedo").val() +
                "&d2=" + $("#dateposle").val() +
                "&login=" + $("#login").val() +
                "&text=" + $("#text").val() +
                "&type=" + $("#type").val();

            $("#ttt").load("/contract/admin/juraudit/tables", param, function () {
                // Вешаем сортировку на таблицу:
                //console.log($("#tab").get(0));
                sortTable($("#tab").get(0), sortAsc1, sortDesc1, sortRules);
            });
            return false;
        }

    });
});