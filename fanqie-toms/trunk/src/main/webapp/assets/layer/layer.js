/*! layer-v1.9x 弹层组件 License LGPL  http://layer.layui.com By 贤心 */
;
!function (a, b) {
    "use strict";
    var c, d, e = {
        getPath: function () {
            var a = document.scripts, b = a[a.length - 1], c = b.src;
            if (!b.getAttribute("merge"))return c.substring(0, c.lastIndexOf("/") + 1)
        }(),
        config: {},
        end: {},
        btn: ["&#x786E;&#x5B9A;", "&#x53D6;&#x6D88;"],
        type: ["dialog", "page", "iframe", "loading", "tips"]
    };
    a.layer = {
        v: "1.9.2",
        ie6: !!a.ActiveXObject && !a.XMLHttpRequest,
        index: 0,
        path: e.getPath,
        config: function (a, b) {
            var d = 0;
            return a = a || {}, e.config = c.extend(e.config, a), layer.path = e.config.path || layer.path, "string" == typeof a.extend && (a.extend = [a.extend]), layer.use("skin/layer.css", a.extend && a.extend.length > 0 ? function f() {
                var c = a.extend;
                layer.use(c[c[d] ? d : d - 1], d < c.length ? function () {
                    return ++d, f
                }() : b)
            }() : b), this
        },
        use: function (a, b, d) {
            var e = c("head")[0], a = a.replace(/\s/g, ""), f = /\.css$/.test(a), g = document.createElement(f ? "link" : "script"), h = "layui_layer_" + a.replace(/\.|\//g, "");
            return layer.path ? (f && (g.rel = "stylesheet"), g[f ? "href" : "src"] = /^http:\/\//.test(a) ? a : layer.path + a, g.id = h, c("#" + h)[0] || e.appendChild(g), function i() {
                (f ? 1989 === parseInt(c("#" + h).css("width")) : layer[d || h]) ? function () {
                    b && b();
                    try {
                        f || e.removeChild(g)
                    } catch (a) {
                    }
                }() : setTimeout(i, 100)
            }(), this) : void 0
        },
        ready: function (a, b) {
            var d = "function" == typeof a;
            return d && (b = a), layer.config(c.extend(e.config, function () {
                return d ? {} : {path: a}
            }()), b), this
        },
        alert: function (a, b, d) {
            var e = "function" == typeof b;
            return e && (d = b), layer.open(c.extend({content: a, yes: d}, e ? {} : b))
        },
        confirm: function (a, b, d, f) {
            var g = "function" == typeof b;
            return g && (f = d, d = b), layer.open(c.extend({content: a, btn: e.btn, yes: d, cancel: f}, g ? {} : b))
        },
        msg: function (a, d, e) {
            var f = "function" == typeof d, h = "layui-layer-msg", i = g.anim.length - 1;
            return f && (e = d), layer.open(c.extend({
                content: a,
                time: 3e3,
                shade: !1,
                skin: h,
                title: !1,
                closeBtn: !1,
                btn: !1,
                end: e
            }, f ? {skin: h + " layui-layer-hui", shift: i} : function () {
                return d = d || {}, (-1 === d.icon || d.icon === b) && (d.skin = h + " " + (d.skin || "layui-layer-hui")), d
            }()))
        },
        load: function (a, b) {
            return layer.open(c.extend({type: 3, icon: a || 0, shade: .01}, b))
        },
        tips: function (a, b, d) {
            return layer.open(c.extend({type: 4, content: [a, b], closeBtn: !1, time: 3e3, maxWidth: 210}, d))
        }
    };
    var f = function (a) {
        var b = this;
        b.index = ++layer.index, b.config = c.extend({}, b.config, e.config, a), b.creat()
    };
    f.pt = f.prototype;
    var g = ["layui-layer", ".layui-layer-title", ".layui-layer-main", ".layui-layer-dialog", "layui-layer-iframe", "layui-layer-content", "layui-layer-btn", "layui-layer-close"];
    g.anim = ["layui-anim", "layui-anim-01", "layui-anim-02", "layui-anim-03", "layui-anim-04", "layui-anim-05", "layui-anim-06"], f.pt.config = {
        type: 0,
        shade: .3,
        fix: !0,
        move: g[1],
        title: "&#x4FE1;&#x606F;",
        offset: "auto",
        area: "auto",
        closeBtn: 1,
        time: 0,
        zIndex: 19891014,
        maxWidth: 360,
        shift: 0,
        icon: -1,
        scrollbar: !0,
        tips: 2
    }, f.pt.vessel = function (a, b) {
        var c = this, d = c.index, f = c.config, h = f.zIndex + d, i = "object" == typeof f.title, j = f.maxmin && (1 === f.type || 2 === f.type), k = f.title ? '<div class="layui-layer-title" style="' + (i ? f.title[1] : "") + '">' + (i ? f.title[0] : f.title) + "</div>" : "";
        return f.zIndex = h, b([f.shade ? '<div class="layui-layer-shade" id="layui-layer-shade' + d + '" times="' + d + '" style="' + ("z-index:" + (h - 1) + "; background-color:" + (f.shade[1] || "#000") + "; opacity:" + (f.shade[0] || f.shade) + "; filter:alpha(opacity=" + (100 * f.shade[0] || 100 * f.shade) + ");") + '"></div>' : "", '<div class="' + g[0] + " " + (g.anim[f.shift] || "") + (" layui-layer-" + e.type[f.type]) + (0 != f.type && 2 != f.type || f.shade ? "" : " layui-layer-border") + " " + (f.skin || "") + '" id="' + g[0] + d + '" type="' + e.type[f.type] + '" times="' + d + '" showtime="' + f.time + '" conType="' + (a ? "object" : "string") + '" style="z-index: ' + h + "; width:" + f.area[0] + ";height:" + f.area[1] + (f.fix ? "" : ";position:absolute;") + '">' + (a && 2 != f.type ? "" : k) + '<div class="layui-layer-content' + (0 == f.type && -1 !== f.icon ? " layui-layer-padding" : "") + (3 == f.type ? " layui-layer-loading" + f.icon : "") + '">' + (0 == f.type && -1 !== f.icon ? '<i class="layui-layer-ico layui-layer-ico' + f.icon + '"></i>' : "") + (1 == f.type && a ? "" : f.content || "") + '</div><span class="layui-layer-setwin">' + function () {
            var a = j ? '<a class="layui-layer-min" href="javascript:;"><cite></cite></a><a class="layui-layer-ico layui-layer-max" href="javascript:;"></a>' : "";
            return f.closeBtn && (a += '<a class="layui-layer-ico ' + g[7] + " " + g[7] + (f.title ? f.closeBtn : 4 == f.type ? "1" : "2") + '" href="javascript:;"></a>'), a
        }() + "</span>" + (f.btn ? function () {
            var a = "";
            "string" == typeof f.btn && (f.btn = [f.btn]);
            for (var b = 0, c = f.btn.length; c > b; b++)a += '<a class="' + g[6] + b + '">' + f.btn[b] + "</a>";
            return '<div class="' + g[6] + '">' + a + "</div>"
        }() : "") + "</div>"], k), c
    }, f.pt.creat = function () {
        var a = this, b = a.config, f = a.index, h = b.content, i = "object" == typeof h;
        switch ("string" == typeof b.area && (b.area = "auto" === b.area ? ["", ""] : [b.area, ""]), b.type) {
            case 0:
                b.btn = "btn"in b ? b.btn : e.btn[0], layer.closeAll("dialog");
                break;
            case 2:
                var h = b.content = i ? b.content : [b.content || "http://sentsin.com?from=layer", "auto"];
                b.content = '<iframe scrolling="' + (b.content[1] || "auto") + '" allowtransparency="true" id="' + g[4] + f + '" name="' + g[4] + f + '" onload="this.className=\'\';" class="layui-layer-load" frameborder="0" src="' + b.content[0] + '"></iframe>';
                break;
            case 3:
                b.title = !1, b.closeBtn = !1, -1 === b.icon && 0 === b.icon, layer.closeAll("loading");
                break;
            case 4:
                i || (b.content = [b.content, "body"]), b.follow = b.content[1], b.content = b.content[0] + '<i class="layui-layer-TipsG"></i>', b.title = !1, b.shade = !1, b.fix = !1, b.tips = "object" == typeof b.tips ? b.tips : [b.tips, !0], b.tipsMore || layer.closeAll("tips")
        }
        a.vessel(i, function (d, e) {
            c("body").append(d[0]), i ? function () {
                2 == b.type || 4 == b.type ? function () {
                    c("body").append(d[1])
                }() : function () {
                    h.parents("." + g[0])[0] || (h.show().addClass("layui-layer-wrap").wrap(d[1]), c("#" + g[0] + f).find("." + g[5]).before(e))
                }()
            }() : c("body").append(d[1]), a.layero = c("#" + g[0] + f), b.scrollbar || g.html.css("overflow", "hidden").attr("layer-full", f)
        }).auto(f), 2 == b.type && layer.ie6 && a.layero.find("iframe").attr("src", h[0]), 4 == b.type ? a.tips() : a.offset(), b.fix && d.on("resize", function () {
            a.offset(), (/^\d+%$/.test(b.area[0]) || /^\d+%$/.test(b.area[1])) && a.auto(f), 4 == b.type && a.tips()
        }), b.time <= 0 || setTimeout(function () {
            layer.close(a.index)
        }, b.time), a.move().callback()
    }, f.pt.auto = function (a) {
        function b(a) {
            a = h.find(a), a.height(i[1] - j - k - 2 * (0 | parseFloat(a.css("padding"))))
        }

        var e = this, f = e.config, h = c("#" + g[0] + a);
        "" === f.area[0] && f.maxWidth > 0 && (/MSIE 7/.test(navigator.userAgent) && f.btn && h.width(h.innerWidth()), h.outerWidth() > f.maxWidth && h.width(f.maxWidth));
        var i = [h.innerWidth(), h.innerHeight()], j = h.find(g[1]).outerHeight() || 0, k = h.find("." + g[6]).outerHeight() || 0;
        switch (f.type) {
            case 2:
                b("iframe");
                break;
            default:
                "" === f.area[1] ? f.fix && i[1] > d.height() && (i[1] = d.height(), b("." + g[5])) : b("." + g[5])
        }
        return e
    }, f.pt.offset = function () {
        var a = this, b = a.config, c = a.layero, e = [c.outerWidth(), c.outerHeight()], f = "object" == typeof b.offset;
        a.offsetTop = (d.height() - e[1]) / 2, a.offsetLeft = (d.width() - e[0]) / 2, f ? (a.offsetTop = b.offset[0], a.offsetLeft = b.offset[1] || a.offsetLeft) : "auto" !== b.offset && (a.offsetTop = b.offset, "rb" === b.offset && (a.offsetTop = d.height() - e[1], a.offsetLeft = d.width() - e[0])), b.fix || (a.offsetTop = /%$/.test(a.offsetTop) ? d.height() * parseFloat(a.offsetTop) / 100 : parseFloat(a.offsetTop), a.offsetLeft = /%$/.test(a.offsetLeft) ? d.width() * parseFloat(a.offsetLeft) / 100 : parseFloat(a.offsetLeft), a.offsetTop += d.scrollTop(), a.offsetLeft += d.scrollLeft()), c.css({
            top: a.offsetTop,
            left: a.offsetLeft
        })
    }, f.pt.tips = function () {
        var a = this, b = a.config, e = a.layero, f = [e.outerWidth(), e.outerHeight()], h = c(b.follow);
        h[0] || (h = c("body"));
        var i = {
            width: h.outerWidth(),
            height: h.outerHeight(),
            top: h.offset().top,
            left: h.offset().left
        }, j = e.find(".layui-layer-TipsG"), k = b.tips[0];
        b.tips[1] || j.remove(), i.autoLeft = function () {
            i.left + f[0] - d.width() > 0 ? (i.tipLeft = i.left + i.width - f[0], j.css({
                right: 12,
                left: "auto"
            })) : i.tipLeft = i.left
        }, i.where = [function () {
            i.autoLeft(), i.tipTop = i.top - f[1] - 10, j.removeClass("layui-layer-TipsB").addClass("layui-layer-TipsT").css("border-right-color", b.tips[1])
        }, function () {
            i.tipLeft = i.left + i.width + 10, i.tipTop = i.top, j.removeClass("layui-layer-TipsL").addClass("layui-layer-TipsR").css("border-bottom-color", b.tips[1])
        }, function () {
            i.autoLeft(), i.tipTop = i.top + i.height + 10, j.removeClass("layui-layer-TipsT").addClass("layui-layer-TipsB").css("border-right-color", b.tips[1])
        }, function () {
            i.tipLeft = i.left - f[0] - 10, i.tipTop = i.top, j.removeClass("layui-layer-TipsR").addClass("layui-layer-TipsL").css("border-bottom-color", b.tips[1])
        }], i.where[k - 1](), 1 === k ? i.top - (d.scrollTop() + f[1] + 16) < 0 && i.where[2]() : 2 === k ? d.width() - (i.left + i.width + f[0] + 16) > 0 || i.where[3]() : 3 === k ? i.top - d.scrollTop() + i.height + f[1] + 16 - d.height() > 0 && i.where[0]() : 4 === k && f[0] + 16 - i.left > 0 && i.where[1](), e.find("." + g[5]).css({
            "background-color": b.tips[1],
            "padding-right": b.closeBtn ? "30px" : ""
        }), e.css({left: i.tipLeft, top: i.tipTop})
    }, f.pt.move = function () {
        var a = this, b = a.config, e = {
            setY: 0, moveLayer: function () {
                var a = e.layero, b = parseInt(a.css("margin-left")), c = parseInt(e.move.css("left"));
                0 === b || (c -= b), "fixed" !== a.css("position") && (c -= a.parent().offset().left, e.setY = 0), a.css({
                    left: c,
                    top: parseInt(e.move.css("top")) - e.setY
                })
            }
        }, f = a.layero.find(b.move);
        return b.move && f.attr("move", "ok"), f.css({cursor: b.move ? "move" : "auto"}), c(b.move).on("mousedown", function (a) {
            if (a.preventDefault(), "ok" === c(this).attr("move")) {
                e.ismove = !0, e.layero = c(this).parents("." + g[0]);
                var f = e.layero.offset().left, h = e.layero.offset().top, i = e.layero.width() - 6, j = e.layero.height() - 6;
                c("#layui-layer-moves")[0] || c("body").append('<div id="layui-layer-moves" class="layui-layer-moves" style="left:' + f + "px; top:" + h + "px; width:" + i + "px; height:" + j + 'px; z-index:2147483584"></div>'), e.move = c("#layui-layer-moves"), b.moveType && e.move.css({visibility: "hidden"}), e.moveX = a.pageX - e.move.position().left, e.moveY = a.pageY - e.move.position().top, "fixed" !== e.layero.css("position") || (e.setY = d.scrollTop())
            }
        }), c(document).mousemove(function (a) {
            if (e.ismove) {
                var c = a.pageX - e.moveX, f = a.pageY - e.moveY;
                if (a.preventDefault(), !b.moveOut) {
                    e.setY = d.scrollTop();
                    var g = d.width() - e.move.outerWidth(), h = e.setY;
                    0 > c && (c = 0), c > g && (c = g), h > f && (f = h), f > d.height() - e.move.outerHeight() + e.setY && (f = d.height() - e.move.outerHeight() + e.setY)
                }
                e.move.css({left: c, top: f}), b.moveType && e.moveLayer(), c = f = g = h = null
            }
        }).mouseup(function () {
            try {
                e.ismove && (e.moveLayer(), e.move.remove()), e.ismove = !1
            } catch (a) {
                e.ismove = !1
            }
            b.moveEnd && b.moveEnd()
        }), a
    }, f.pt.callback = function () {
        function a() {
            var a = f.cancel && f.cancel(b.index);
            a === !1 || layer.close(b.index)
        }

        var b = this, d = b.layero, f = b.config;
        b.openLayer(), f.success && (2 == f.type ? d.find("iframe")[0].onload = function () {
            this.className = "", f.success(d, b.index)
        } : f.success(d, b.index)), layer.ie6 && b.IE6(d), d.find("." + g[6]).children("a").on("click", function () {
            var e = c(this).index();
            0 === e ? f.yes ? f.yes(b.index, d) : layer.close(b.index) : 1 === e ? a() : f["btn" + (e + 1)] ? f["btn" + (e + 1)](b.index, d) : layer.close(b.index)
        }), d.find("." + g[7]).on("click", a), f.shadeClose && c("#layui-layer-shade" + b.index).on("click", function () {
            layer.close(b.index)
        }), d.find(".layui-layer-min").on("click", function () {
            layer.min(b.index, f), f.min && f.min(d)
        }), d.find(".layui-layer-max").on("click", function () {
            c(this).hasClass("layui-layer-maxmin") ? (layer.restore(b.index), f.restore && f.restore(d)) : (layer.full(b.index, f), f.full && f.full(d))
        }), f.end && (e.end[b.index] = f.end)
    }, e.reselect = function () {
        c.each(c("select"), function () {
            var a = c(this);
            a.parents("." + g[0])[0] || 1 == a.attr("layer") && c("." + g[0]).length < 1 && a.removeAttr("layer").show(), a = null
        })
    }, f.pt.IE6 = function (a) {
        function b() {
            a.css({top: f + (e.config.fix ? d.scrollTop() : 0)})
        }

        var e = this, f = a.offset().top;
        b(), d.scroll(b), c("select").each(function () {
            var a = c(this);
            a.parents("." + g[0])[0] || "none" === a.css("display") || a.attr({layer: "1"}).hide(), a = null
        })
    }, f.pt.openLayer = function () {
        var a = this;
        layer.zIndex = a.config.zIndex, layer.setTop = function (a) {
            var b = function () {
                layer.zIndex++, a.css("z-index", layer.zIndex + 1)
            };
            return layer.zIndex = parseInt(a[0].style.zIndex), a.on("mousedown", b), layer.zIndex
        }
    }, e.record = function (a) {
        var b = [a.outerWidth(), a.outerHeight(), a.position().top, a.position().left + parseFloat(a.css("margin-left"))];
        a.find(".layui-layer-max").addClass("layui-layer-maxmin"), a.attr({area: b})
    }, e.rescollbar = function (a) {
        g.html.attr("layer-full") == a && (g.html[0].style.removeProperty ? g.html[0].style.removeProperty("overflow") : g.html[0].style.removeAttribute("overflow"), g.html.removeAttr("layer-full"))
    }, layer.getChildFrame = function (a, b) {
        return b = b || c("." + g[4]).attr("times"), c("#" + g[0] + b).find("iframe").contents().find(a)
    }, layer.getFrameIndex = function (a) {
        return c("#" + a).parents("." + g[4]).attr("times")
    }, layer.iframeAuto = function (a) {
        if (a) {
            var b = layer.getChildFrame("body", a).outerHeight(), d = c("#" + g[0] + a), e = d.find(g[1]).outerHeight() || 0, f = d.find("." + g[6]).outerHeight() || 0;
            d.css({height: b + e + f}), d.find("iframe").css({height: b})
        }
    }, layer.iframeSrc = function (a, b) {
        c("#" + g[0] + a).find("iframe").attr("src", b)
    }, layer.style = function (a, b) {
        var d = c("#" + g[0] + a), f = d.attr("type"), h = d.find(g[1]).outerHeight() || 0, i = d.find("." + g[6]).outerHeight() || 0;
        (f === e.type[1] || f === e.type[2]) && (d.css(b), f === e.type[2] && d.find("iframe").css({height: parseFloat(b.height) - h - i}))
    }, layer.min = function (a) {
        var b = c("#" + g[0] + a), d = b.find(g[1]).outerHeight() || 0;
        e.record(b), layer.style(a, {
            width: 180,
            height: d,
            overflow: "hidden"
        }), b.find(".layui-layer-min").hide(), "page" === b.attr("type") && b.find(g[4]).hide(), e.rescollbar(a)
    }, layer.restore = function (a) {
        {
            var b = c("#" + g[0] + a), d = b.attr("area").split(",");
            b.attr("type")
        }
        layer.style(a, {
            width: parseFloat(d[0]),
            height: parseFloat(d[1]),
            top: parseFloat(d[2]),
            left: parseFloat(d[3]),
            overflow: "visible"
        }), b.find(".layui-layer-max").removeClass("layui-layer-maxmin"), b.find(".layui-layer-min").show(), "page" === b.attr("type") && b.find(g[4]).show(), e.rescollbar(a)
    }, layer.full = function (a) {
        var b, f = c("#" + g[0] + a);
        e.record(f), g.html.attr("layer-full") || g.html.css("overflow", "hidden").attr("layer-full", a), clearTimeout(b), b = setTimeout(function () {
            var b = "fixed" === f.css("position");
            layer.style(a, {
                top: b ? 0 : d.scrollTop(),
                left: b ? 0 : d.scrollLeft(),
                width: d.width(),
                height: d.height()
            }), f.find(".layui-layer-min").hide()
        }, 100)
    }, layer.title = function (a, b) {
        var d = c("#" + g[0] + (b || layer.index)).find(g[1]);
        d.html(a)
    }, layer.close = function (a) {
        var b = c("#" + g[0] + a), d = b.attr("type");
        if (b[0]) {
            if (d === e.type[1] && "object" === b.attr("conType")) {
                b.children(":not(." + g[5] + ")").remove();
                for (var f = 0; 2 > f; f++)b.find(".layui-layer-wrap").unwrap().hide()
            } else {
                if (d === e.type[2])try {
                    var h = c("#" + g[4] + a)[0];
                    h.contentWindow.document.write(""), h.contentWindow.close(), b.find("." + g[5])[0].removeChild(h)
                } catch (i) {
                }
                b[0].innerHTML = "", b.remove()
            }
            c("#layui-layer-moves, #layui-layer-shade" + a).remove(), layer.ie6 && e.reselect(), e.rescollbar(a), "function" == typeof e.end[a] && e.end[a](), delete e.end[a]
        }
    }, layer.closeAll = function (a) {
        c.each(c("." + g[0]), function () {
            var b = c(this), d = a ? b.attr("type") === a : 1;
            d && layer.close(b.attr("times")), d = null
        })
    }, e.run = function () {
        c = jQuery, d = c(a), g.html = c("html"), layer.open = function (a) {
            var b = new f(a);
            return b.index
        }
    }, "function" == typeof define ? define(function () {
        return e.run(), layer
    }) : function () {
        e.run(), layer.use("skin/layer.css")
    }()
}(window);