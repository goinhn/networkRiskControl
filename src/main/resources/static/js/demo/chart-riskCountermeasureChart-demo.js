//初始化图表
var myRiskCountermeasureChart = echarts.init(document.getElementById("myRiskCountermeasureChart"));
let startChartTimeCountermeasure = 0;
let endChartTimeCountermeasure = 50;
var xData = function () {
    var data = [];
    for (var i = 1; i < 61; i++) {
        data.push(i);
    }
    return data;
}();

var optionRiskCountermeasureChart = {
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
        x: "2%",
        x2: "16%",
        "borderWidth": 0,
        "top": 35,
        "bottom": 60,//x轴的文字距离底部
        textStyle: {
            color: "#fff"
        }
    },
    "legend": {
        type: 'scroll',
        orient: 'vertical',
        // orient: 'horizontal', // 若设置为垂直则为'vertical'
        x2: 0,
        top: 0,
        bottom: 20,
        textStyle: {
            color: '#90979c',
        },
        "data": [],
        selected: []
    },
    "calculable": true,
    "xAxis": [{
        name: "时间",
        nameTextStyle: {
            padding: [0, 0, 30, -40]
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
        name: "风险值",
        nameTextStyle: {
            padding: [0, 0, -20, 50]
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
        min: 0,
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
    "series": [
    ]
};


var drawRiskCountermeasureChartLine = function (startRiskTime, trafficInfos) {
    for (let i = 0, len = optionRiskCountermeasureChart.series.length; i < len; i++) {
        optionRiskCountermeasureChart.series[i].data = [];
    }
    for (let key in trafficInfos) {
        let trafficInfo = trafficInfos[key];
        let [totalNumbers, attackNumbers, normalNumbers, riskValues] = trafficInfo;
        let newRiskValues = new Array(startRiskTime);
        let newRiskAttackNumbers = new Array(startRiskTime);
        let newRiskNormalNumbers = new Array(startRiskTime);
        for (let i = 0, len = startRiskTime + riskValues.length; i < len; i++) {
            if (i < startRiskTime - 1) {
                newRiskValues[i] = undefined;
                newRiskAttackNumbers[i] = undefined;
                newRiskNormalNumbers[i] = undefined;
            } else {
                newRiskValues[i] = parseFloat(riskValues[i - startRiskTime + 1]).toFixed(2);
                newRiskAttackNumbers[i] = attackNumbers[i - startRiskTime + 1];
                newRiskNormalNumbers[i] = normalNumbers[i - startRiskTime + 1];
            }
        }
        optionRiskCountermeasureChart.series[cmNameToIndex[key]].data = newRiskValues;
    }
    myRiskCountermeasureChart.setOption(optionRiskCountermeasureChart);
};
// 使用刚指定的配置项和数据显示图表
myRiskCountermeasureChart.setOption(optionRiskCountermeasureChart);

myRiskCountermeasureChart.on('legendselectchanged', function (obj) {
    var selected = obj.selected;
    var legend = obj.name;

    if (selected !== undefined) {
        if (selectedLegend[legend]) {
            selectedLegend[legend] = false;
        } else {
            selectedLegend[legend] = true;
        }
        optionRiskCountermeasureChart.legend.selected = selectedLegend;
        optionRiskCountermeasureChart.dataZoom[0].start = startChartTimeCountermeasure;
        optionRiskCountermeasureChart.dataZoom[0].end = endChartTimeCountermeasure;
        myRiskCountermeasureChart.setOption(optionRiskCountermeasureChart);
        console.log(legend + "--" + selectedLegend[legend]);
        /*if (isFirstUnSelect(selected)) {
            triggerAction('legendToggleSelect', selected);
        } else if (isAllUnSelected(selected)) {
            triggerAction('legendSelect', selected);

        }*/
    }

});


myRiskCountermeasureChart.on('datazoom', function (params) {
    startChartTimeCountermeasure = params.start;
    endChartTimeCountermeasure = params.end;
});

//自动缩放
window.addEventListener("resize", () => {
    myRiskCountermeasureChart.resize();
});