/*! layer弹层拓展类 */
;
layer.use("skin/layer.ext.css", function () {
    layer.layui_layer_extendlayerextjs = !0
}), layer.prompt = function (a, b) {
    a = a || {}, "function" == typeof a && (b = a);
    var c, d = 2 == a.formType ? '<textarea class="layui-layer-input">' + (a.value || "") + "</textarea>" : function () {
        return '<input type="' + (1 == a.formType ? "password" : "text") + '" class="layui-layer-input" value="' + (a.value || "") + '">'
    }();
    return layer.open($.extend({
        btn: ["&#x786E;&#x5B9A;", "&#x53D6;&#x6D88;"],
        content: d,
        skin: "layui-layer-prompt",
        success: function (a) {
            c = a.find(".layui-layer-input"), c.focus()
        },
        yes: function (d) {
            var e = c.val();
            "" === e ? c.focus() : e.length > (a.maxlength || 500) ? layer.tips("&#x6700;&#x591A;&#x8F93;&#x5165;" + (a.maxlength || 500) + "&#x4E2A;&#x5B57;&#x6570;", c, {tips: 1}) : b && b(e, d, c)
        }
    }, a))
}, layer.tab = function (a) {
    a = a || {};
    var b = a.tab || {};
    return layer.open($.extend({
        type: 1, skin: "layui-layer-tab", title: function () {
            var a = b.length, c = 1, d = "";
            if (a > 0)for (d = '<span class="layui-layer-tabnow">' + b[0].title + "</span>"; a > c; c++)d += "<span>" + b[c].title + "</span>";
            return d
        }(), content: '<ul class="layui-layer-tabmain">' + function () {
            var a = b.length, c = 1, d = "";
            if (a > 0)for (d = '<li class="layui-layer-tabli xubox_tab_layer">' + (b[0].content || "no content") + "</li>"; a > c; c++)d += '<li class="layui-layer-tabli">' + (b[c].content || "no  content") + "</li>";
            return d
        }() + "</ul>", success: function (a) {
            var b = a.find(".layui-layer-title").children(), c = a.find(".layui-layer-tabmain").children();
            b.on("mousedown", function (a) {
                a.stopPropagation ? a.stopPropagation() : a.cancelBubble = !0;
                var b = $(this), d = b.index();
                b.addClass("layui-layer-tabnow").siblings().removeClass("layui-layer-tabnow"), c.eq(d).show().siblings().hide()
            })
        }
    }, a))
}, layer.photos = function (a, b, c) {
    function d(a, b, c) {
        var d = new Image;
        d.onload = function () {
            d.onload = null, b(d)
        }, d.onerror = function (a) {
            d.onload = null, c(a)
        }, d.src = a
    }

    var e = {};
    if (a = a || {}, a.photos) {
        var f = a.photos.constructor === Object, g = f ? a.photos : {}, h = g.data || [], i = g.start || 0;
        if (e.imgIndex = i + 1, f) {
            if (0 === h.length)return void layer.msg("&#x6CA1;&#x6709;&#x56FE;&#x7247;")
        } else {
            var j = $(a.photos), k = j.find(a.img || "img");
            if (0 === k.length)return;
            if (b || j.find(g.img || "img").each(function (b) {
                    var c = $(this);
                    h.push({
                        alt: c.attr("alt"),
                        pid: c.attr("layer-pid"),
                        src: c.attr("layer-src") || c.attr("src"),
                        thumb: c.attr("src")
                    }), c.on("click", function () {
                        layer.photos($.extend(a, {photos: {start: b, data: h, tab: a.tab}, full: a.full}), !0)
                    })
                }), !b)return
        }
        e.imgprev = function (a) {
            e.imgIndex--, e.imgIndex < 1 && (e.imgIndex = h.length), e.tabimg(a)
        }, e.imgnext = function (a) {
            e.imgIndex++, e.imgIndex > h.length && (e.imgIndex = 1), e.tabimg(a)
        }, e.keyup = function (a) {
            if (!e.end) {
                var b = a.keyCode;
                a.preventDefault(), 37 === b ? e.imgprev(!0) : 39 === b ? e.imgnext(!0) : 27 === b && layer.close(e.index)
            }
        }, e.tabimg = function (b) {
            g.start = e.imgIndex - 1, layer.close(e.index), layer.photos({photos: g, full: a.full, tab: a.tab}, !0, b)
        }, e.event = function () {
            e.bigimg.hover(function () {
                e.imgsee.show()
            }, function () {
                e.imgsee.hide()
            }), e.bigimg.find(".layui-layer-imgprev").on("click", function (a) {
                a.preventDefault(), e.imgprev()
            }), e.bigimg.find(".layui-layer-imgnext").on("click", function (a) {
                a.preventDefault(), e.imgnext()
            }), $(document).on("keyup", e.keyup)
        }, e.loadi = layer.load(1, {shade: .9, scrollbar: !1}), d(h[i].src, function (b) {
            layer.close(e.loadi), e.index = layer.open($.extend({
                type: 1,
                area: function () {
                    var c = [b.width, b.height], d = [$(window).width() - 100, $(window).height() - 100];
                    return !a.full && c[0] > d[0] && (c[0] = d[0], c[1] = c[0] * d[1] / c[0]), [c[0] + "px", c[1] + "px"]
                }(),
                title: !1,
                shade: .9,
                shadeClose: !0,
                closeBtn: !1,
                move: ".layui-layer-phimg img",
                moveType: 1,
                scrollbar: !1,
                moveOut: !0,
                shift: 5 * Math.random() | 0,
                skin: "layui-layer-photos",
                content: '<div class="layui-layer-phimg"><img src="' + h[i].src + '" alt="' + (h[i].alt || "") + '" layer-pid="' + h[i].pid + '"><div class="layui-layer-imgsee"><span class="layui-layer-imguide"><a href="javascript:;" class="layui-layer-iconext layui-layer-imgprev"></a><a href="javascript:;" class="layui-layer-iconext layui-layer-imgnext"></a></span><div class="layui-layer-imgbar" style="display:' + (c ? "block" : "") + '"><span class="layui-layer-imgtit"><a href="javascript:;">' + (h[i].alt || "") + "</a><em>" + e.imgIndex + "/" + h.length + "</em></span></div></div></div>",
                success: function (b) {
                    e.bigimg = b.find(".layui-layer-phimg"), e.imgsee = b.find(".layui-layer-imguide,.layui-layer-imgbar"), e.event(b), a.tab && a.tab(h[i], b)
                },
                end: function () {
                    e.end = !0, $(document).off("keyup", e.keyup)
                }
            }, a))
        }, function () {
            layer.close(e.loadi), layer.msg("&#x5F53;&#x524D;&#x56FE;&#x7247;&#x5730;&#x5740;&#x5F02;&#x5E38;", function () {
                h.length > 1 && e.imgnext(!0)
            })
        })
    }
};