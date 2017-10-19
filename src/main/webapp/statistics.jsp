<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
    <meta charset="utf-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title></title>

    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="../assets/materialize/css/materialize.min.css" media="screen,projection"/>
    <!-- Bootstrap Styles-->
    <link href="../assets/css/bootstrap.css" rel="stylesheet"/>
    <!-- FontAwesome Styles-->
    <link href="../assets/css/font-awesome.css" rel="stylesheet"/>
    <!-- Morris Chart Styles-->
    <link href="../assets/js/morris/morris-0.4.3.min.css" rel="stylesheet"/>
    <!-- Custom Styles-->
    <link href="../assets/css/custom-styles.css" rel="stylesheet"/>
    <!-- Google Fonts-->
    <link href='http://fonts.googleapis.com/css?family=Open+Sans' rel='stylesheet' type='text/css'/>
    <link rel="stylesheet" href="../assets/js/Lightweight-Chart/cssCharts.css">
    <!-- jQuery Js -->
    <script src="../assets/js/jquery-1.10.2.js"></script>
    <!-- Highcharts JS -->
    <script src="../assets/js/highcharts.js"></script>
    <script src="../assets/js/exporting.js"></script>
    <script src="../assets/js/highcharts-zh_CN.js"></script>
    <%--<script src="https://img.hcharts.cn/highcharts/themes/dark-unica.js"></script>--%>

</head>
<body>
<div id="wrapper">
    <nav class="navbar navbar-default top-navbar" role="navigation">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand waves-effect waves-dark" href="index.jsp"><i class="large material-icons">insert_chart</i>
                <strong>中科天合</strong></a>

            <div id="sideNav" href=""><i class="material-icons dp48">toc</i></div>
        </div>

        <ul class="nav navbar-top-links navbar-right">
            <%--<li><a class="dropdown-button waves-effect waves-dark" href="#!" data-activates="dropdown4"><i class="fa fa-envelope fa-fw"></i> <i class="material-icons right">arrow_drop_down</i></a></li>--%>
            <%--<li><a class="dropdown-button waves-effect waves-dark" href="#!" data-activates="dropdown3"><i class="fa fa-tasks fa-fw"></i> <i class="material-icons right">arrow_drop_down</i></a></li>--%>
            <%--<li><a class="dropdown-button waves-effect waves-dark" href="#!" data-activates="dropdown2"><i class="fa fa-bell fa-fw"></i> <i class="material-icons right">arrow_drop_down</i></a></li>--%>
            <li><a class="dropdown-button waves-effect waves-dark" href="#!" data-activates="dropdown1"><i
                    class="fa fa-user fa-fw"></i> <b>ICT WSN</b> <i class="material-icons right">arrow_drop_down</i></a>
            </li>
        </ul>
    </nav>
    <!-- Dropdown Structure -->
    <ul id="dropdown1" class="dropdown-content">
        <li><a href="#"><i class="fa fa-user fa-fw"></i> 账户</a>
        </li>
        <li><a href="#"><i class="fa fa-gear fa-fw"></i> 设置</a>
        </li>
        <li><a href="#"><i class="fa fa-sign-out fa-fw"></i> 退出</a>
        </li>
    </ul>


    <!--/. NAV TOP  -->
    <nav class="navbar-default navbar-side" role="navigation">
        <div class="sidebar-collapse">
            <ul class="nav" id="main-menu">

                <%--<li>
                    <a href="index.jsp" class="waves-effect waves-dark"><i class="fa fa-dashboard"></i> Dashboard</a>
                </li>
                <li>
                    <a href="ui-elements.jsp" class="waves-effect waves-dark"><i class="fa fa-desktop"></i> UI Elements</a>
                </li>
                <li>
                    <a href="chart.jsp" class="waves-effect waves-dark"><i class="fa fa-bar-chart-o"></i> Charts</a>
                </li>
                <li>
                    <a href="tab-panel.jsp" class="waves-effect waves-dark"><i class="fa fa-qrcode"></i> Tabs & Panels</a>
                </li>

                <li>
                    <a href="table.jsp" class="waves-effect waves-dark"><i class="fa fa-table"></i> Responsive Tables</a>
                </li>
                <li>
                    <a href="form.jsp" class="waves-effect waves-dark"><i class="fa fa-edit"></i> Forms </a>
                </li>


                <li>
                    <a href="#" class="waves-effect waves-dark"><i class="fa fa-sitemap"></i> Multi-Level Dropdown<span class="fa arrow"></span></a>
                    <ul class="nav nav-second-level">
                        <li>
                            <a href="#">Second Level Link</a>
                        </li>
                        <li>
                            <a href="#">Second Level Link</a>
                        </li>
                        <li>
                            <a href="#">Second Level Link<span class="fa arrow"></span></a>
                            <ul class="nav nav-third-level">
                                <li>
                                    <a href="#">Third Level Link</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Link</a>
                                </li>
                                <li>
                                    <a href="#">Third Level Link</a>
                                </li>

                            </ul>

                        </li>
                    </ul>
                </li>--%>
                <li>
                    <a class="active-menu waves-effect waves-dark" href="empty.jsp"><i class="fa fa-fw fa-file"></i>
                        后台统计</a>
                </li>
            </ul>

        </div>

    </nav>
    <!-- /. NAV SIDE  -->
    <div id="page-wrapper">
        <div class="header">
            <h1 class="page-header">
                智能孝子后台统计
                <%--<small>请求次数&下载流量</small>--%>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#">后台</a></li>
                <li><a class="active">统计</a></li>
            </ol>

        </div>
        <div id="page-inner">

            <div class="row">

                <div class="col-md-12">
                    <div class="card">
                        <div class="card-action">
                            智能孝子机器人后台数据请求流量统计
                        </div>
                        <%--<div class="card-content">--%>
                        <%--<p>智能孝子机器人后台数据请求流量统计</p>--%>
                        <%--<div class="clearBoth"><br/></div>--%>

                        <%--</div>--%>

                        <div class="card-content">
                            <div class="table-responsive">
                                <table class="table table-striped table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th>统计类别</th>
                                        <th>当天</th>
                                        <th>当月</th>
                                        <th>当年</th>
                                        <th>累计</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th>请求频率</th>
                                        <td>${day_req}次</td>
                                        <td>${month_req}次</td>
                                        <td>${year_req}次</td>
                                        <td>${all_req}次</td>
                                    </tr>
                                    <tr>
                                        <th>下载流量</th>
                                        <td>${day_flow}M</td>
                                        <td>${month_flow}M</td>
                                        <td>${year_flow}M</td>
                                        <td>${all_flow}M</td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div id="container" style="min-width:400px;height:400px;"></div>

                    <script>
                        var charts = null;
                        charts = {
                            chart: {
                                renderTo: 'container',
                                defaultSeriesType: 'spline' //图表类型为曲线图
                            },
                            title: {
                                text: "近15天机器人后端调用情况",
                                style: {
                                    fontSize: '24px',
                                    fontWeight: 'bold'
                                }
                            },
                            xAxis: {
                                categories: []
                            },
                            yAxis: [{
                                lineWidth: 1,
                                title: {
                                    text: '下载流量(兆)',
                                    style: {
                                        color: '#EE0F42',
                                        fontSize: '16px',
                                        fontWeight: 'bold'
                                    }
                                },
                                labels: {
                                    formatter: function () {
                                        return this.value + '兆';
                                    },
                                    style: {
                                        fontWeight: 'bold'
                                    }
                                }
                            }, {
                                title: {
                                    text: '请求频率(次)',
                                    style: {
                                        color: '#5EB0EE',
                                        fontSize: '16px',
                                        fontWeight: 'bold'
                                    }
                                },
                                labels: {
                                    formatter: function () {
                                        return this.value + '次';
                                    },
                                    style: {
                                        fontWeight: 'bold'
                                    }
                                },
                                lineWidth: 1,
                            }],
                            credits: {
                                enabled: true,
                                href: "http://www.zhongketianhe.com.cn/",
                                text: "北京中科天合科技有限公司"
                            },
                            tooltip: {
                                crosshairs: true,
                                shared: true
                            },
                            plotOptions: {
                                spline: {
                                    marker: {
                                        radius: 4,
                                        lineColor: '#666666',
                                        lineWidth: 1
                                    }
                                }
                            },
                            series: [{
                                data: [],
                                name: '下载流量',
                                color: '#EE0F42',
                                tooltip: {
                                    valueSuffix: 'M'
                                },
                                yAxis: 0
                            }, {
                                data: [],
                                yAxis: 1,
                                name: '请求频率',
                                color: '#5EB0EE',
                                tooltip: {
                                    valueSuffix: '次'
                                },
                            }]

                        };
                        //ajax请求数据
                        $.ajax({
                            url: '<%=path %>/web/req?company_id=1001',
                            type: 'GET',     // 请求类型，常用的有 GET 和 POST
                            data: {
                                // 请求参数
                                company_id: 1001
                            },
                            success: function (data) { // 接口调用成功回调函数
                                // data 为服务器返回的数据
                                var obj = eval('(' + data + ')');
                                charts.xAxis.categories = obj.date;
                                charts.series[0].data = obj.flow;
                                charts.series[1].data = obj.req;
                                var chart = new Highcharts.Chart(charts);
                            }
                        });
                        //

                    </script>


                </div>
                <!-- /. PAGE INNER  -->

            </div>
            <!-- /. PAGE WRAPPER  -->
        </div>

    </div>

</div>
<!-- /. WRAPPER  -->
<!-- JS Scripts-->


<!-- Bootstrap Js -->
<script src="../assets/js/bootstrap.min.js"></script>

<script src="../assets/materialize/js/materialize.min.js"></script>

<!-- Metis Menu Js -->
<script src="../assets/js/jquery.metisMenu.js"></script>
<!-- Custom Js -->
<script src="../assets/js/custom-scripts.js"></script>


</body>

</html>
