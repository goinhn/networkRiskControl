//初始化图表
var myRiskChart = echarts.init(document.getElementById("myRiskChart"));

var xData = function () {
    var data = [];
    for (var i = 1; i < 60; i++) {
        data.push(i);
    }
    return data;
}();

var optionRiskChart = {
    "title": {
        x: "4%",

        textStyle: {
            color: '#fff',
            fontSize: '22'
        },
        subtextStyle: {
            color: '#90979c',
            fontSize: '16',

        },
    },
    "tooltip": {
        "trigger": "axis",
        "axisPointer": {
            "type": "shadow",
            textStyle: {
                color: "#fff"
            }

        },
    },
    "grid": {
        x: "4%",
        x2: "4%",
        "borderWidth": 0,
        "top": 35,
        "bottom": 60,//x轴的文字距离底部
        textStyle: {
            color: "#fff"
        }
    },
    "legend": {
        orient: 'horizontal', // 若设置为垂直则为'vertical'
        x: '10%',
        top: '5%',
        textStyle: {
            color: '#90979c',
        },
        "data": ['正常流量', '威胁流量', '风险值']
    },
    "calculable": true,
    "xAxis": [{
        name: "时间",
        nameTextStyle: {
            padding: [0, 0, 20, -50]
        },
        "type": "category",
        "axisLine": {
            lineStyle: {
                color: '#90979c'
            }
        },
        "splitLine": {
            "show": false
        },
        "axisTick": {
            "show": false
        },
        "splitArea": {
            "show": false
        },
        "axisLabel": {
            "interval": 0,

        },
        "data": xData,
    }],
    "yAxis": [{
        name: "流量包数量",
        nameTextStyle: {
            padding: [0, 0, 0, 0]
        },
        "type": "value",
        "splitLine": {
            "show": false
        },
        "axisLine": {
            lineStyle: {
                color: '#90979c'
            }
        },
        "axisTick": {
            "show": false
        },
        "axisLabel": {
            "interval": 0,

        },
        "splitArea": {
            "show": false
        },
        min: 0
    }, {
        name: "风险值",
        nameTextStyle: {
            padding: [0, 0, -20, -50]
        },
        "type": "value",
        "splitLine": {
            "show": false
        },
        "axisLine": {
            lineStyle: {
                color: '#90979c'
            }
        },
        "axisTick": {
            "show": false
        },
        "axisLabel": {
            "interval": 0,

        },
        "splitArea": {
            "show": false
        },
        min: -5,
        max: 10

    }],
    "dataZoom": [{
        "show": true,
        "height": 25,
        "xAxisIndex": [
            0
        ],
        bottom: 5,//缩放轴离底部的宽度
        "start": 0,
        "end": 50,
        handleIcon: 'path://M306.1,413c0,2.2-1.8,4-4,4h-59.8c-2.2,0-4-1.8-4-4V200.8c0-2.2,1.8-4,4-4h59.8c2.2,0,4,1.8,4,4V413z',
        handleSize: '110%',
        handleStyle: {
            color: "#d3dee5",

        },
        textStyle: {
            color: "#fff"
        },
        borderColor: "#90979c"


    }, {
        "type": "inside",
        "show": true,
        zoomLock: true,
        "height": 15,
        "start": 1,
        "end": 35
    }],
    "series": [{//series的三个依次是：女、男、总数
        "name": "正常流量",
        "type": "bar",
        "stack": "总量",
        "barMaxWidth": 35,
        "barGap": "5%",
        "itemStyle": {
            "normal": {
                "color": "rgba(0,191,183,1)",
                "label": {
                    "show": true,
                    "textStyle": {
                        "color": "#fff"
                    },
                    "position": "insideTop",
                    formatter: function (p) {
                        return p.value > 0 ? (p.value) : '';
                    }
                }
            }
        },
        yAxisIndex: 0,
        "data": [],
    }, {
        "name": "威胁流量",
        "type": "bar",
        "stack": "总量",
        "itemStyle": {
            "normal": {
                "color": "rgba(255,144,128,1)",
                "barBorderRadius": 0,
                "label": {
                    "show": true,
                    "position": "top",
                    formatter: function (p) {
                        return p.value > 0 ? (p.value) : '';
                    }
                }
            }
        },
        yAxisIndex: 0,
        "data": []
    }, {
        "name": "风险值",
        "type": "line",
        // "stack": "总量",
        symbolSize: 10,
        symbol: 'circle',
        yAxisIndex: 1,
        "itemStyle": {
            "normal": {
                "color": "#4e73df",
                "barBorderRadius": 0,
                "label": {
                    "show": true,
                    "position": "top",
                    formatter: function (p) {
                        return p.value > 0 ? (p.value) : '';
                    }
                }
            }
        },
        "data": []
    },
    ]
};

/*设定监听器，用于监听缩放的范围
params的数据结构为：
{
    type: 'datazoom',
    // 缩放的开始位置的百分比，0 - 100
    start: number
    // 缩放的结束位置的百分比，0 - 100
    end: number
    // 缩放的开始位置的数值，只有在工具栏的缩放行为的事件中存在。
    startValue?: number
    // 缩放的结束位置的数值，只有在工具栏的缩放行为的事件中存在。
    endValue?: number
}*/

//测试用，用于测试获取用户使用滑块得到的范围和动态画图的功能
/*let a = 10;
myRiskChart.on('datazoom', function (params) {
    // console.log('Start Time: ' + params.start);
    // console.log('End Time: ' + params.end);
    let startTime = params.start;
    let endTime = params.end;

    // console.log(optionRiskChart.series[2]);
    // console.log("Start: " + startTime + " End: " + endTime);
    if (a === 0) {

        //找到当前的开始和结束月份
        let startMonth = Math.ceil(startTime / (100 / 12));
        let endMonth = Math.ceil(endTime / (100 / 12));

        var trafficPeriod = document.getElementById("traffic-period");
        trafficPeriod.innerText = (startMonth + 1) + "月~" + (endMonth + 1) + "月";
        //空的data数组
        let newData = new Array(endMonth);
        //全部的数据
        // let data = [ 1036, 3693, 2962, 3810, 2519, 1915, 1748, 4675, 6209, 4323, 2865, 4298, 2000 ];
        let data = [1, 3, 2, 3, 2, 1, 7, 4, 6, 4, 5, 9];
        //在空的data数组中的相应位置赋值
        for (let i = startMonth; i <= endMonth; i++) {
            newData[i] = data[i];
        }
        // newData = [ 1036, 3693, 2962, 3810, 2519, 1915, 1748, 4675, 6209, 4323, 2865, 4298, 2000 ];
        //赋值并更改时间轴的显示
        optionRiskChart.series[2].data = newData;
        optionRiskChart.dataZoom[0].start = startTime;
        optionRiskChart.dataZoom[0].end = endTime;
        myRiskChart.setOption(optionRiskChart);
        console.log("New Data: " + newData);
        a--;
        //设定每秒更新一次总流量的节点
        let k = 0;
        var myTimer = setInterval(function () {
            k++;
            optionRiskChart.series[2].data.push(data[endMonth + k]);
            optionRiskChart.dataZoom[0].end += 100 / 12;
            myRiskChart.setOption(optionRiskChart);
            if (k + endMonth + 1 >= 12) {//如果已经到了最后一个
                clearInterval(myTimer);
                optionRiskChart.series.push({
                    "name": "总流量-1",
                    "type": "line",
                    // "stack": "总量",
                    symbolSize: 10,
                    symbol: 'circle',
                    yAxisIndex: 1,
                    "itemStyle": {
                        "normal": {
                            "color": "#36b9cc",
                            "barBorderRadius": 0,
                            "label": {
                                "show": true,
                                "position": "top",
                                formatter: function (p) {
                                    return p.value > 0 ? (p.value) : '';
                                }
                            }
                        }
                    },
                    "data": [6.3, 3.5, 4.1, 2.3, 1.9, 9.1, 6.8, 8.5, 6.3, 2.1, 1.7, 8.2]
                });
                optionRiskChart.legend.data.push("总流量-1");
                myRiskChart.setOption(optionRiskChart);
                return;
            }

            console.log(endMonth + "---" + k);
        }, 1000);

        let inputStartTime = document.getElementById("input-start-time");
        let inputEndTime = document.getElementById("input-end-time");

        inputStartTime.value = startMonth;
        inputEndTime.value = endMonth;

    } else {
        a--;
    }
    /!*if (a === 5) {//测试最上面的曲线能不能只画一部分let newData = [undefined, undefined, undefined, 3810, 2519, 1915, 1748, 4675, 6209, 4323, 2865, 4298, 2000];
        option.series[2].data = newData;
        myNetworkTopologyChart.setOption(option);
        a--;
    }*!/
    /!*if (a > 0) {//测试能不能再临时增加流量
        option.series[0].data.push(parseInt(params.start * 100));
        option.series[1].data.push(parseInt(params.end * 100));
        option.series[2].data.push(parseInt((params.start + params.end) * 100));
        option.xAxis[0].data.push('一月份');
        myNetworkTopologyChart.setOption(option);
        a--;
    }*!/

});*/

var drawRiskChartLine = function (startRiskTime, riskValues, attackNumbers, normalNumbers) {

    let newRiskValues = new Array(startRiskTime);
    let newRiskAttackNumbers = new Array(startRiskTime);
    let newRiskNormalNumbers = new Array(startRiskTime);
    let newRiskTotalNumbers = new Array(startRiskTime);
    for (let i = 0, len = startRiskTime + riskValues.length; i < len; i++) {
        if (i < startRiskTime - 1) {
            newRiskValues[i] = undefined;
            newRiskAttackNumbers[i] = undefined;
            newRiskNormalNumbers[i] = undefined;
            newRiskTotalNumbers[i] = undefined;
        } else {
            newRiskValues[i] = parseFloat(riskValues[i - startRiskTime + 1]).toFixed(2);
            newRiskAttackNumbers[i] = parseInt(attackNumbers[i - startRiskTime + 1]);
            newRiskNormalNumbers[i] = parseInt(normalNumbers[i - startRiskTime + 1]);
            newRiskTotalNumbers[i] = newRiskNormalNumbers[i] + newRiskAttackNumbers[i];
        }
    }
    console.log(newRiskValues);

    // if (newRiskValues[0] === )

    let maxNumber = 0;
    for (let i = 0; i < newRiskTotalNumbers.length; i++) {
        if ((newRiskTotalNumbers[i] !== undefined) && (!isNaN(newRiskTotalNumbers[i]))) {
            if (newRiskTotalNumbers[i] > maxNumber) {
                maxNumber = newRiskTotalNumbers[i];
            }
        }
    }
    optionRiskChart.series[0].data = newRiskAttackNumbers;
    optionRiskChart.series[1].data = newRiskNormalNumbers;
    optionRiskChart.series[2].data = newRiskValues;
    optionRiskChart.yAxis[0].max = maxNumber + 20;
    console.log("MAXNUNBER:::" + maxNumber);
    myRiskChart.setOption(optionRiskChart);
};
// 使用刚指定的配置项和数据显示图表
myRiskChart.setOption(optionRiskChart);

//自动缩放
window.addEventListener("resize", () => {
    myRiskChart.resize();
});