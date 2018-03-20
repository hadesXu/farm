
$(document).ready(function () {
    //如果输入法顶上时，去掉底部flex定位。
    //  var h=$(window).height();
    //  $(window).resize(function() {
    //      if($(window).height()<h-100){
    //          $('footer').hide();
    //      }else{
    //      	$('footer').show();
    //      }
    //  });

    //控制出现输入框时，高度固定。
    //	var h = $(window).height();
    //  $(window).resize(function(){
    //  	console.log($(window).height())
    //      if ($(window).height() < h) {
    //          $(".warp-page").css("height",h + "px");
    //      }else{
    //          $(".warp-page").css("height","100%");
    //      }
    //  });
    //头部轮播
    var appendNumber = 4;
    var prependNumber = 1;
    var swiper = new Swiper('.swiper-container', {
        pagination: '.swiper-pagination',
        nextButton: '.swiper-button-next',
        prevButton: '.swiper-button-prev',
        slidesPerView: 6,
        // centeredSlides: true,
        paginationClickable: true,
        spaceBetween: 5,
    });
    //弹窗
    function TanWind(oid, cid, sid) {
		
        var $oid = $("." + oid),
            $cid = $("." + cid),
            $sid = $("." + sid);
        $oid.click(function () {
            var $this = $(this);
            var price = $this.data("price");
            var productid = $this.data("productid");
            if (price != null && productid != null) {
                $this.addClass("shop-li02").siblings(".shop-li02").removeClass('shop-li02');
                $('#product_price').text(price);
                $('#productid').val(productid);
                $('#input_num').val(1);
            }
            $sid.show();
        });
        $cid.click(function () {
            $sid.hide();
        })
    };

    TanWind("up-box", "close", "popup-box");	//商店弹窗
	TanWind("up-box1", "close", "popup-box1");	//商店弹窗
	TanWind("up-box2", "close", "popup-box2");	//商店弹窗
	TanWind("up-box3", "close", "popup-box3");	//商店弹窗
	TanWind("up-box4", "close", "popup-box4");	//商店弹窗
	
    TanWind("up-buy", "up-buy01", "buy-box");  //购买弹窗
    TanWind("up-buy01", "up-buy", "buy01-box"); //切换购买弹窗
    TanWind("reg-open", "close-r", "reg-box");//绑定手机弹窗
    TanWind("up-open", "close-up", "top-up"); //充值页面

    //tab 商店物品列表
    $(".tab-con").eq(0).show();
    $(".tab-btn span").click(function () {
        var num = $(this).data("num");
        var target = $(this).data("target");
        $(".tab-con").hide();
        $(".tab-btn span").removeClass("a-tab00 a-tab01 a-tab02");
        $(target).show();
        $(this).addClass("a-tab0" + num);
    });
    //喂狗粮
    $(".tools02").click(function () {
        $(".eating").show();
        setTimeout(function () {
            $(".eating").hide();
        }, 2000)
    })
});

//普通商品加减
$('.buy01-box .btn-jian').on('click', function () {
    var buy_num = $('#input_num').val();
    var pay_money = $('#product_price').text();
    var product_peice = pay_money / buy_num;
    if (isNaN(buy_num) || buy_num % 1 != 0 || buy_num < 2) buy_num = 2;
    buy_num = parseInt(buy_num) - 1;
    $('#input_num').val(buy_num);
    $('#product_price').text(product_peice * buy_num);
});
$('.buy01-box .btn-add').on('click', function () {
    var buy_num = $('#input_num').val();
    var pay_money = $('#product_price').text();
    var product_peice = pay_money / buy_num;
    if (isNaN(buy_num) || buy_num % 1 != 0 || buy_num < 1) buy_num = 0;
    buy_num = parseInt(buy_num) + 1;
    $('#input_num').val(buy_num);
    $('#product_price').text(product_peice * buy_num);
});
//普通商品加减

//饲料商品加减
$('.buy-box .btn-jian').on('click', function () {
    var buy_num = $('#input_num2').val();
    if (isNaN(buy_num) || buy_num % 1 != 0 || buy_num < 2) buy_num = 2;
    buy_num = parseInt(buy_num) - 1;
    $('#input_num2').val(buy_num);
});
$('.buy-box .btn-add').on('click', function () {
    var buy_num = $('#input_num2').val();
    if (isNaN(buy_num) || buy_num % 1 != 0 || buy_num < 1) buy_num = 0;
    buy_num = parseInt(buy_num) + 1;
    $('#input_num2').val(buy_num);
});
//饲料商品加减

$('#now-buy-btn,#now-buy-btn2').on('click', function () {
    var productid = $('#productid').val();
    var target = $(this).data('target');
    var pay_method = 1;
    if (target == '#input_num2') {
        pay_method = $('#select').val();
    }
    var buynumber = $(target).val();
    if (isNaN(productid) || productid <= 0) {
        layer.open({
            content: '您选择的产品不存在,请重试'
            , skin: 'msg'
            , time: 3 //2秒后自动关闭
        });
        return;
    }
    if (isNaN(buynumber) || buynumber <= 0) {
        layer.open({
            content: '购买数量输入错误,请重试'
            , skin: 'msg'
            , time: 3 //2秒后自动关闭
        });
        return;
    }
    layer.open({
        type: 2
        , content: '正在提交到订单系统...'
        , shadeClose: false
    });
    ExecAJ("breed_buy_product", new Array(0, 0, productid, buynumber, pay_method), "_to_pay_result");
});
function _to_pay_result(text) {
    layer.closeAll();
    layer.open({
        content: text
        , btn: '我知道了'
        , end: function () {
            if (text.indexOf('支付成功') >= 0) {
                window.location.reload();
            }
        }
    });
}

var breed_type_id = 9;
var feed_orderid = '';

//begin 喂养
$('li[data-feed],b[data-feed_orderid]').on('click', function () {
    breed_type_id = $(this).data('breedtypeid');
    feed_orderid = $(this).data('feed_orderid');
    if (typeof (feed_orderid) == "undefined") { feed_orderid = '' }
    layer.open({
        type: 2
        , content: '获取喂养数据...'
        , shadeClose: false
    });
    ExecAJ("breed_product_feed", new Array(0, 0, breed_type_id, 0, feed_orderid), "_result_feed");
});
function _result_feed(text) {
    layer.closeAll();
    if (text.indexOf('一共') >= 0) {
        layer.open({
            content: text
            , btn: ['喂食', '取消']
            , yes: function (index) {
                layer.close(index);
                layer.open({
                    type: 2
                    , content: '正在提交到订单系统...'
                    , shadeClose: false
                });
                ExecAJ("breed_product_feed", new Array(0, 0, breed_type_id, 1, feed_orderid), "_pay_feed_result");
            }
        });
        return;
    }
    layer.open({
        content: text
        , btn: '我知道了'
    });

}
function _pay_feed_result(text) {
    layer.closeAll();
    layer.open({
        content: text
        , btn: '我知道了'
    });
}
//end 喂养

//begin 收获
$('li[data-harvest]').on('click', function () {
    breed_type_id = $(this).data('breedtypeid');
    layer.open({
        type: 2
        , content: '正在收获中...'
        , shadeClose: false
    });
    ExecAJ("breed_product_harvest", new Array(0, 0, breed_type_id), "_result_harvest");
});
function _result_harvest(text) {
    layer.closeAll();
    layer.open({
        content: text
        , btn: '我知道了'
    });
}
//end 收获

//begin 进入场景
$('a[data-type]').on('click', function () {
    var type = $(this).data('type');
    layer.open({
        type: 2
        , content: '正在进入中...'
        , shadeClose: false
    });
    ExecAJ("breed_usert_url", new Array(0, 0, type), "_to_user_style");
});
function _to_user_style(url) {
    layer.closeAll();
    if (url.indexOf('.aspx') > 0) {
        window.location.href = url;
    }
    else {
        layer.open({
            content: url
            , btn: '我知道了'
        });
    }
}
//end 进入场景

//begin 狗狗喂食

$('#dog_feed').on('click', function () {
    breed_type_id = $(this).data('breedtypeid');
    layer.open({
        type: 2
        , content: '获取喂养数据...'
        , shadeClose: false
    });
    ExecAJ("breed_dog_feed", new Array(0, 0, 0, 0), "_result_dog_feed");
});
function _result_dog_feed(text) {
    layer.closeAll();
    if (text.indexOf('支付') >= 0) {
        var pay_method = 0;
        layer.open({
            content: text + "<select id='pay_method' class='form-control input-sm' style='width:120px;margin: 2px auto;'><option value='2'>使用银币支付</option><option value='1'>使用金币支付</option></select>"
            , btn: ['支付', '取消']
            , yes: function (index) {
                pay_method = $('#pay_method').val();
                layer.close(index);
                layer.open({
                    type: 2
                    , content: '正在提交到订单系统...'
                    , shadeClose: false
                });
                ExecAJ("breed_dog_feed", new Array(0, 0, 1, pay_method), "_pay_dog_feed_result");
            }
        });
        return;
    }
    layer.open({
        content: text
        , btn: '我知道了'
    });

}
function _pay_dog_feed_result(text) {
    layer.closeAll();
    layer.open({
        content: text
        , btn: '我知道了'
    });
}

//end 狗狗喂食
//layer.closeAll();