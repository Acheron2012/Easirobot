<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%String path = request.getContextPath(); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script src="<%=path %>/assets/js/jquery-1.10.2.js"></script>

    <script src="<%=path %>/assets/js/bootstrap.min.js"></script>

    <link href="<%=path %>/assets/css/bootstrap.css" rel="stylesheet"/>

    <script src="<%=path %>/assets/js/bootstrapValidator.js"></script>
    <link href="<%=path %>/assets/css/bootstrapValidator.css" rel="stylesheet"/>

    <title>Insert title here</title>
    <%
        String[] arealist = new String[16];
        arealist[0] = "下城区";
        arealist[1] = "拱墅区";
        arealist[2] = "富阳区";
        arealist[3] = "临安";
        arealist[4] = "下沙经济开发区";
        arealist[5] = "上城区";
        arealist[6] = "西湖区";
        arealist[7] = "建德";
        arealist[8] = "桐庐";
        arealist[9] = "西湖风景名胜区";
        arealist[10] = "淳安";
        arealist[11] = "大江东产业集聚区";
        arealist[12] = "江干区";
        arealist[13] = "滨江区";
        arealist[14] = "萧山区";
        arealist[15] = "余杭区";
        String name = "";
        String sex = "";
        int age = 0;
        String phone = "";
        String identity_card = "";
        //String provience = "";
        String provience = "浙江省";
        //String city = "";
        String city = "杭州";
        String area = "";
        String street = "";
        String address = "";
        String service_status = "";
        String admin_name = "";
        String company_name = "";
        String weight = "";
        String height = "";
    %>
    <script type="text/javascript">
        //定义了城市的二维数组，里面的顺序跟省份的顺序是相同的。通过selectedIndex获得省份的下标值来得到相应的城市数组
        var streetlist = [
            ["潮鸣街道", "东新街道", "天水街道", "武林街道", "朝晖街道", "长庆街道", "石桥街道", "文晖街道"],
            ["湖墅街道", "小河街道", "米市巷街道", "和睦街道", "大关街道", "拱宸桥街道", "祥符街道", "半山街道", "康桥街道", "上塘街道"],
            ["富春街道", "春江街道", "东洲街道", "鹿山街道", "常绿镇", "场口镇", "春建乡", "大源镇", "洞桥镇", "高桥镇", "常安镇", "受降镇", "里山镇", "灵桥镇", "龙门镇", "渌渚镇", "万市镇", "新 登镇", "胥口镇", "永昌镇", "湖源乡", "新桐乡", "上官乡", "渔山乡", "环山乡"],
            ["锦城街道", "玲珑街道", "锦南街道 ", "青山湖街道", "锦北街道", "板桥镇", "高虹镇", "太湖源镇", "於潜镇", "天目山镇", "太阳镇", "潜川 镇", "昌化镇", "龙岗镇", "河桥镇", "湍口镇", "清凉峰镇", "岛石镇 "],
            ["下沙街道", "白杨街道"],
            ["紫阳街道", "湖滨街道", "清波街道", "望江街道", "小营街道", "南星街道"],
            ["北山街道", "西溪街道", "灵隐街道", "翠苑街道", "文新街道", "古荡街道", "留下街道", "转塘街道", "蒋村街道", "三墩镇", " 双浦镇"],
            ["新安江街道", "更楼街道", "洋溪街道", "梅城镇", "寿昌镇", "大同镇", "乾潭镇", "三都镇", "杨村桥镇", "下涯镇", "大慈岩镇", "航头镇", "大洋镇", "莲花镇", "李家镇", "钦堂乡"],
            ["城南街道", "桐君街道", "旧县街道", "富春江镇", "凤川街道", "江南镇", "分水镇", "横村镇", "百江镇", "瑶琳镇", "合村乡", "钟山乡", "新合乡", "莪山畲族乡"],
            ["西湖街道"],
            ["千岛湖镇", "文昌镇", "石林镇", "枫树岭镇", "大墅镇", "中洲镇", "临岐镇", "威坪镇", "姜家镇", "梓桐镇", "汾口镇", "左口乡", "屏门乡", "金峰乡", "瑶山乡", "鸠坑乡", "王阜乡", "宋村乡", "富文乡", "里商乡", "安阳乡", "界首乡", "浪川乡", "中州镇"],
            ["义蓬街道", "河庄街道", "临江街道", "前进街道"],
            ["闸弄口街道", "凯旋街道", "采荷街道", "四季青 街道", "笕桥镇", "彭埠镇", "丁桥镇", "九堡镇", "白杨街道", "下沙街道"],
            ["西兴街道", "长河街道", "浦沿街道"],
            ["北干街道", "蜀山街道", "新塘街道", "靖江街道", "南阳街道", "党湾镇", "瓜沥镇", "坎山镇", "党山镇", "益农镇", "闻堰镇", "新街镇", "宁围镇", "衙前镇", "浦阳镇", "进化镇", "所前镇", "楼塔镇", "河上镇", "戴村镇", "临浦镇", "义桥镇", "新湾街道", "城厢街道", "围垦区", "萧山经济开发区", "萧山商业城", "萧山湘湖旅 游度假区"],
            ["运河街道", "乔司街道", "崇贤街道", "东湖街道", "南苑街道", "星桥街道", "余杭街道", " 闲林街道", "仓前街道", "中泰街道", "五常街道", "良渚街道", "仁和街道", "塘栖镇", "瓶窑镇", "径山镇", "黄湖镇", "鸬鸟镇", "百丈镇", "临平街道"]
        ];

        function getStreet() {

            //获得区域下拉框的对象
            var sltArea = document.getElementsByName("user_area")[0];
            //获得街道下拉框的对象
            var sltStreet = document.getElementsByName("user_street")[0];

            //得到对应区域的街道数组
            var areaStreet = streetlist[sltArea.selectedIndex - 1];
            //清空街道下拉框，仅留提示选项
            sltStreet.length = 1;

            //将街道数组中的值填充到街道下拉框中
            for (var i = 0; i < areaStreet.length; i++) {
                sltStreet[i + 1] = new Option(areaStreet[i], areaStreet[i]);
            }

            <%--var street_value = <%=street%>;--%>
            <%--if(street_value!="" || street_value!=null){--%>
            <%--sltStreet.value=street_value;--%>
            <%--}--%>
        }
    </script>
</head>
<body>
<center><h1>用户信息修改</h1></center>
<div class="col-md-6 col-md-offset-4">
    <%--<a href="<%=path %>/rob/beacon/message">获取音频</a>--%>
    <form id="defaultForm" action="<%=path %>/user/update" method="post">
        <div class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">用户ID</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="user_id" value=""/>
                </div>
            </div>
        </div>

        <h1></h1>

        <div class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">姓名</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="user_name" value=""/>
                </div>
            </div>
        </div>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">性别</label>
                <div class="col-md-5">
                    <select class="form-control" name="user_sex">
                        <option value="0" selected="selected" %>女</option>
                        <option value="1">男</option>
                    </select>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">年龄</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="user_age" value=""/>
                </div>
            </div>
        </row>

        <h1></h1>
        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">身高</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" placeholder="厘米" name="user_height" value=""/>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">体重</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" placeholder="公斤" name="user_weight" value=""/>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">身份证</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="identity_card" value=""/>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">省份</label>
                <div class="col-md-5">
                    <%-- <input type="text" class="form-control" name="userprovince" readonly="readonly" value="<%=provience %>"/> --%>
                    <input type="text" class="form-control" name="user_province" readonly="readonly" value="浙江省"/>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">城市</label>
                <div class="col-md-5">
                    <%-- <input type="text" class="form-control" name="usercity" readonly="readonly" value="<%=city %>"/> --%>
                    <input type="text" class="form-control" name="user_city" readonly="readonly" value="杭州"/>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">区域</label>
                <div class="col-md-5">
                    <%-- <input type="text" class="form-control" name="userarea" value="<%=area %>"/> --%>
                    <select class="form-control" name="user_area" onChange="getStreet()">
                        <option value="">-- 选择所在区 --</option>

                        <%
                            for (int i = 0; i < arealist.length; i++) {
                        %>
                        <option value="<%=arealist[i]%>"
                                <%if(arealist[i].equals(area)) { %>selected="selected"<%} %>><%=arealist[i] %>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">街道</label>
                <div class="col-md-5">
                    <%-- <input type="text" class="form-control" name="userstreet" value="<%=street %>"/> --%>
                    <select class="form-control" name="user_street">
                        <option value="">-- 选择所在街道--</option>
                    </select>
                </div>
            </div>
        </row>

        <h1></h1>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">住址</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="user_address" value="<%=address %>"/>
                </div>
            </div>
        </row>

        <h1></h1>
        <%--<row class="row">--%>
        <%--<div class="form-group">--%>
        <%--<label class="col-md-1 control-label">公寓号</label>--%>
        <%--<div class="col-md-5">--%>
        <%--<input type="text" class="form-control" name="userapartmentname"/>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</row>--%>
        <%--</fieldset>--%>

        <row class="row">
            <div class="form-group">
                <label class="col-md-1 control-label">老人 电话</label>
                <div class="col-md-5">
                    <input type="text" class="form-control" name="user_phone" value=""/>
                </div>
            </div>
        </row>

        <h1></h1>


        <%--&lt;%&ndash;<hr style=" height:2px;border:none;border-top:2px dotted #185598;" />&ndash;%&gt;--%>
        <row class="row">
            <div class="form-group">
                <div class="col-md-4 col-lg-offset-2">
                    <button type="submit" class="btn btn-primary">提 交</button>
                </div>
            </div>
        </row>
        <row>
            <div class="form-group">
                <div class="col-md-4 col-lg-offset-2">
                    <font color="red"> ${message }</font>
                </div>
            </div>
        </row>
    </form>
</div>


</body>

<script type="text/javascript">
    $(document).ready(function () {
        $('#defaultForm').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                user_name: {
                    message: '姓名不能为空',
                    validators: {
                        notEmpty: {
                            message: '姓名不能为空'
                        },
                        stringLength: {
                            min: 2,
                            max: 20,
                            message: '您的输入的姓名太长或太短哦'
                        },
                        regexp: {
                            regexp: /^[\u4E00-\u9FA5.]+$/,
                            message: '请输入您的中文全名'
                        }
                    }
                },
                user_province: {
                    validators: {
                        notEmpty: {
                            message: '省份不能为空'
                        }
                    }
                },
                user_city: {
                    validators: {
                        notEmpty: {
                            message: '城市不能为空'
                        }
                    }
                },
                user_area: {
                    validators: {
                        notEmpty: {
                            message: '区域不能为空'
                        }
                    }
                },
                user_street: {
                    validators: {
                        notEmpty: {
                            message: '街道不能为空'
                        }
                    }
                },
//                usergroup: {
//                    validators: {
//                        notEmpty: {
//                            message: '所属支部不能为空'
//                        }
//                    }
//                },
//                usercompany: {
//                    validators: {
//                        notEmpty: {
//                            message: '服务公司不能为空'
//                        }
//                    }
//                },
                user_address: {
                    message: '住址不能为空',
                    validators: {
                        notEmpty: {
                            message: '住址不能为空'
                        },
                    }
                },
                /* userapartmentname: {
                 message: '公寓号不能为空',
                 validators: {
                 notEmpty: {
                 message: '公寓号不能为空'
                 },
                 regexp: {
                 regexp: /^[\w]+$/,
                 message: '请输入数字或者字母表示的公寓号'
                 }
                 }
                 }, */
//                acceptTerms: {
//                    validators: {
//                        notEmpty: {
//                            message: '您需要同意授权'
//                        }
//                    }
//                },
                user_phone: {
                    validators: {
                        phone: {
                            message: '您输入的不是一个有效的手机号码',
                            country: 'CN'
                        },
                        notEmpty: {
                            message: '电话不能为空'
                        }
                    }
                },
                user_age: {
                    validators: {
                        lessThan: {
                            value: 120,
                            inclusive: true,
                            message: '年龄不能超过120'
                        },
                        greaterThan: {
                            value: 10,
                            inclusive: false,
                            message: '年龄不能少于10'
                        },
                        notEmpty: {
                            message: '年龄不能为空'
                        }
                    }
                },
                user_sex: {
                    validators: {
                        notEmpty: {
                            message: '性别不能为空'
                        }
                    }
                },
                user_weight: {
                    validators: {
                        notEmpty: {
                            message: '体重不能为空'
                        }
                    }
                },
                user_height: {
                    validators: {
                        notEmpty: {
                            message: '身高不能为空'
                        }
                    }
                },
                identity_card: {
                    message: '身份证号不能为空',
                    validators: {
                        notEmpty: {
                            message: '身份证号不能为空'
                        },
                        regexp: {
                            regexp: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
                            message: '请输入正确的身份证号码'
                        }
                    }
                },
            }
        });
    });


</script>
</html>