let inputStartTime = document.getElementById("input-start-time");
let inputEndTime = document.getElementById("input-end-time");
let inputStartTimeCountermeasure = document.getElementById("input-start-time-countermeasure");
let inputEndTimeCountermeasure = document.getElementById("input-end-time-countermeasure");
let multiselectCountermeasures = $("#multiselect-countermeasures");
let inputTimeRangeMinute = $("#input-time-range-minute");
let inputTimeRangeMinuteCountermeasure = $("#input-time-range-minute-countermeasure");

let httpUrl = "http://localhost:8080";
let startRiskTime = 0;
let tfcInfo = null;
let cmNameToIndex = {};
let selectedLegend = {};
let prevSelectedLegend = [];
let startHourTime = 0;
let startMinuteTime = 0;
let startHourTimeCountermeasure = 0;
let startMinuteTimeCountermeasure = 0;

$(function () {
    $("#input-time-range-hour").selectpicker({
        width: "5rem"
    });
    $("#input-time-range-hour").selectpicker('refresh');

    $("#input-time-range-hour-countermeasure").selectpicker({
        width: "5rem"
    });
    $("#input-time-range-hour-countermeasure").selectpicker('refresh');

    inputTimeRangeMinute.selectpicker({
        width: "5rem"
    });
    for (let i = 1; i < 61; i++) {
        inputTimeRangeMinute.append($("<option value=" + i + ">第" + i + "分钟</option>"));
    }
    inputTimeRangeMinute.selectpicker('refresh');


    inputTimeRangeMinuteCountermeasure.selectpicker({
        width: "5rem"
    });
    for (let i = 1; i < 61; i++) {
        inputTimeRangeMinuteCountermeasure.append($("<option value=" + i + ">第" + i + "分钟</option>"));
    }
    inputTimeRangeMinuteCountermeasure.selectpicker('refresh');

    //Map<String, Map<String, List<Map<String, String[]>>>>
    $.ajax({
        type: "GET",
        url: httpUrl + '/riskCountermeasure/queryCountermeasures',
        dataType: "json",
        success: function (result) {
            initCountermeasureData(result).then(
                function (nameToIndex) {
                    cmNameToIndex = nameToIndex;
                    myRiskCountermeasureChart.setOption(optionRiskCountermeasureChart);
                    generateCmCheckboxes(nameToIndex);

                }
            );

        },
        error: function (error) {
            console.log("ERROR!");
        }
    });
});

//发送post请求
let riskRestfulPost = function (reqRoute, tfcInputJson) {
    $.ajax({
        type: "POST",
        url: httpUrl + reqRoute,
        dataType: "json",
        data: tfcInputJson,
        contentType: "application/json;charset=utf-8",
        success: function (result) {
            // alert("Success!");
            // console.log(result);
            formatTrafficData(result).then(
                function (trafficData) {
                    let [totalNumbers, attackNumbers, normalNumbers, riskValues] = trafficData;
                    drawRiskChartLine(startRiskTime, riskValues, attackNumbers, normalNumbers);
                }
            );

        },
        error: function (result) {
            alert("Error!");
            console.log(result);
            console.log('错误')
        }
    })
};

//发送post请求
let riskCountermeasureRestfulPost = function (reqRoute, tfcInputJson) {
    $.ajax({
        type: "POST",
        url: httpUrl + reqRoute,
        dataType: "json",
        data: tfcInputJson,
        contentType: "application/json;charset=utf-8",
        success: function (result) {
            // alert("Success!");
            formatTrafficCountermeasureData(result).then(
                function (trafficInfo) {
                    $("#btn-best-risk-countermeasure").removeClass("disabled");
                    //console.log(trafficInfo);
                    tfcInfo = trafficInfo;
                    // let [totalNumbers, attackNumbers, normalNumbers, riskValues] = trafficData;
                    drawRiskCountermeasureChartLine(startRiskTime, trafficInfo);
                }
            );

        },
        error: function (result) {
            alert("Error!");
            console.log(result);
            console.log('错误')
        }
    })
};

//发送post请求
let bestCountermeasureRestfulPost = function (reqRoute, tfcInputJson) {//获得最优的控制机制
    $.ajax({
        type: "POST",
        url: httpUrl + reqRoute,
        dataType: "json",
        data: tfcInputJson,
        contentType: "application/json;charset=utf-8",
        success: function (result) {
            console.log('成功')
            $("#bestcm").text("");
            for (let key in result) {//需要写展示的代码
                $("#bestcm").append(key + "<br />");
            }
        },
        error: function (result) {
            console.log('错误')
        }
    })
};


var startRiskCalculate = function () {
    /*var timeInfoMessage = JSON.stringify({
        "stime": [(1424231119 + parseInt(inputStartTime.value)).toString() + '-' + (1424231119 + parseInt(inputEndTime.value)), "between"],
        "ltime": [(1424231119 + parseInt(inputStartTime.value)).toString() + '-' + (1424231119 + parseInt(inputEndTime.value)), "between"],
    });*/
    if (inputStartTime.value === '') {
        alert('请输入演化开始时间');
        return null;
    } else {
        startRiskTime = parseInt(inputStartTime.value);
    }
    if (inputEndTime.value === '') {
        alert('请输入演化结束时间');
        return null;
    }
    if (parseInt(inputStartTime.value) > parseInt(inputEndTime.value)) {
        alert('演化结束时间不应该小于开始时间');
        return null;
    }
    var timeInfoMessage = JSON.stringify({
        "stime": [(1424231119 + startHourTime + startMinuteTime + startRiskTime).toString() + '-' + (1424231119 + startHourTime + startMinuteTime + parseInt(inputEndTime.value)), "between"],
    });
    console.log(timeInfoMessage);
    return timeInfoMessage;
};

var startRiskCalculateCountermeasure = function () {
    /*var timeInfoMessage = JSON.stringify({
        "stime": [(1424231119 + parseInt(inputStartTime.value)).toString() + '-' + (1424231119 + parseInt(inputEndTime.value)), "between"],
        "ltime": [(1424231119 + parseInt(inputStartTime.value)).toString() + '-' + (1424231119 + parseInt(inputEndTime.value)), "between"],
    });*/

    if (inputStartTimeCountermeasure.value === '') {
        alert('请输入演化开始时间');
        return null;
    } else {
        startRiskTime = parseInt(inputStartTimeCountermeasure.value);
    }
    if (inputEndTimeCountermeasure.value === '') {
        alert('请输入演化结束时间');
        return null;
    }
    if (parseInt(inputStartTimeCountermeasure.value) > parseInt(inputEndTimeCountermeasure.value)) {
        alert('演化结束时间不应该小于开始时间');
        return null;
    }
    startRiskTime = parseInt(inputStartTimeCountermeasure.value);
    var timeInfoMessage = JSON.stringify({
        "stime": [(1424231119 + startHourTimeCountermeasure + startMinuteTimeCountermeasure + startRiskTime).toString() + '-' + (1424231119 + startHourTimeCountermeasure + startMinuteTimeCountermeasure + parseInt(inputEndTimeCountermeasure.value)), "between"],
    });
    console.log(timeInfoMessage);
    return timeInfoMessage;
};


var formatTrafficData = function (trafficData) {
    return new Promise(function (resolve, reject) {
        //"0-0-0-4.98"
        let totalNumbers = [];
        let attackNumbers = [];
        let normalNumbers = [];
        let riskValues = [];
        for (let i = 0, len = trafficData.length; i < len; i++) {
            let tfcData = trafficData[i];
            let [totalNumber, attackNumber, normalNumber, riskValue] = tfcData.split('-');
            console.log([totalNumber, attackNumber, normalNumber, riskValue]);
            totalNumbers.push(totalNumber);
            attackNumbers.push(attackNumber);
            normalNumbers.push(normalNumber);
            riskValues.push(riskValue);
        }
        resolve([totalNumbers, attackNumbers, normalNumbers, riskValues]);
    });
};

var formatTrafficCountermeasureData = function (trafficDatas) {
    return new Promise(function (resolve, reject) {
        let trafficInfo = {};
        for (let key in trafficDatas) {
            let trafficData = trafficDatas[key];
            let totalNumbers = [];
            let attackNumbers = [];
            let normalNumbers = [];
            let riskValues = [];
            for (let i = 0, len = trafficData.length; i < len; i++) {
                let tfcData = trafficData[i];
                let [totalNumber, attackNumber, normalNumber, riskValue] = tfcData.split('-');
                // console.log([totalNumber, attackNumber, normalNumber, riskValue]);
                totalNumbers.push(totalNumber);
                attackNumbers.push(attackNumber);
                normalNumbers.push(normalNumber);
                riskValues.push(riskValue);
            }
            trafficInfo[key] = [totalNumbers, attackNumbers, normalNumbers, riskValues];
        }
        resolve(trafficInfo);
    });
};

var formatCountermeasuresName = function (trafficDatas) {
    return new Promise(function (resolve, reject) {
        let trafficInfo = {};
        for (let key in trafficDatas) {
            let trafficData = trafficDatas[key];
            let totalNumbers = [];
            let attackNumbers = [];
            let normalNumbers = [];
            let riskValues = [];
            for (let i = 0, len = trafficData.length; i < len; i++) {
                let tfcData = trafficData[i];
                let [totalNumber, attackNumber, normalNumber, riskValue] = tfcData.split('-');
                // console.log([totalNumber, attackNumber, normalNumber, riskValue]);
                totalNumbers.push(totalNumber);
                attackNumbers.push(attackNumber);
                normalNumbers.push(normalNumber);
                riskValues.push(riskValue);
            }
            trafficInfo[key] = [totalNumbers, attackNumbers, normalNumbers, riskValues];
        }
        resolve(trafficInfo);
    });
};

var initCountermeasureData = function (countermeasureNames) {
    return new Promise(function (resolve, reject) {
        let result = {};
        optionRiskCountermeasureChart.legend.data = countermeasureNames;
        for (let i = 0; i < countermeasureNames.length; i++) {
            optionRiskCountermeasureChart.series.push({
                "name": countermeasureNames[i],
                "type": "line",
                symbolSize: 10,
                symbol: 'circle',
                "itemStyle": {
                    "normal": {
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
            });
            if (countermeasureNames[i] === "Normal-None-0") {
                selectedLegend[countermeasureNames[i]] = true;
            } else {
                selectedLegend[countermeasureNames[i]] = false;
            }
            result[countermeasureNames[i]] = i;
        }
        optionRiskCountermeasureChart.legend.selected = selectedLegend;
        resolve(result);
    });
};

var generateCmCheckboxes = function (countermeasureNames) {
    let devicesToCountermeasure = {};
    for (let countermeasureName in countermeasureNames) {
        let deviceName = countermeasureName.split('-')[0];
        if (devicesToCountermeasure.hasOwnProperty(deviceName)) {
            devicesToCountermeasure[deviceName].push(countermeasureName.substring(countermeasureName.indexOf('-') + 1));
        } else {
            devicesToCountermeasure[deviceName] = [countermeasureName.substring(countermeasureName.indexOf('-') + 1)];
        }
    }

    for (let deviceName in devicesToCountermeasure) {
        let optGroup = $("<optgroup label=" + deviceName + ">" + "</optgroup>");
        // multiselectCountermeasures.append($("<optgroup label=" + deviceName +">"));
        for (let i = 0; i < devicesToCountermeasure[deviceName].length; i++) {
            optGroup.append($("<option value=" + deviceName + "-" + devicesToCountermeasure[deviceName][i] + ">" + devicesToCountermeasure[deviceName][i] + "</option>"));
        }
        multiselectCountermeasures.append(optGroup);
        // multiselectCountermeasures.append($("</optgroup>"));
    }
    multiselectCountermeasures.selectpicker('refresh');

};

$("#btn-start-risk-calculate").click(function () {
    // inputStartTime.value;
    let timeInfoMessage = startRiskCalculate();
    let reqRoute = '/risk/riskEvolution';
    if (timeInfoMessage !== null) {
        riskRestfulPost(reqRoute, timeInfoMessage);
    }

});

$("#btn-start-risk-countermeasure-calculate").click(function () {
    // inputStartTime.value;

    let timeInfoMessage = startRiskCalculateCountermeasure();
    let reqRoute = '/riskCountermeasure/riskEvolution';
    if (timeInfoMessage !== null) {
        riskCountermeasureRestfulPost(reqRoute, timeInfoMessage);
    }


});
$("#btn-best-risk-countermeasure").click(function () {
    let reqRoute = '/riskCountermeasure/bestCountermeasure';
    let data = {};
    for (let key in tfcInfo) {
        let info = tfcInfo[key];
        let risk = info[3];
        data[key] = risk;
    }
    bestCountermeasureRestfulPost(reqRoute, JSON.stringify(data));
});

$("#multiselect-countermeasures").on('changed.bs.select', function (e) {
    console.log($("#multiselect-countermeasures").val());
    let userSelected = $("#multiselect-countermeasures").val();

    let setCurrentSelected = new Set(userSelected);
    let setPrevSelected = new Set(prevSelectedLegend);
    let diffSelectedSet;

    if (setCurrentSelected.size > setPrevSelected.size) {//prev和current只会相差1
        diffSelectedSet = new Set([...setCurrentSelected].filter(x => !setPrevSelected.has(x)));
        let diffSelect = Array.from(diffSelectedSet)[0];
        selectedLegend[diffSelect] = true;
    } else if (setCurrentSelected.size <= setPrevSelected.size) {
        diffSelectedSet = new Set([...setPrevSelected].filter(x => !setCurrentSelected.has(x)));
        let diffSelect = Array.from(diffSelectedSet)[0];
        selectedLegend[diffSelect] = false;
    }
    prevSelectedLegend = userSelected;

    /*for (let i = 0, len = userSelected.length; i < len; i++) {
        selectedLegend[userSelected[i]] = true;
    }*/
    optionRiskCountermeasureChart.legend.selected = selectedLegend;
    myRiskCountermeasureChart.setOption(optionRiskCountermeasureChart);
});

$("#input-time-range-hour").on('changed.bs.select', function (e) {
    console.log($("#input-time-range-hour").val());
    let selectedHour = parseInt($("#input-time-range-hour").val());
    startHourTime = (selectedHour - 1) * 3600;
});
$("#input-time-range-minute").on('changed.bs.select', function (e) {
    console.log($("#input-time-range-minute").val());
    let selectedMinute = parseInt($("#input-time-range-minute").val());
    startMinuteTime = (selectedMinute - 1) * 60;
});

$("#input-time-range-hour-countermeasure").on('changed.bs.select', function (e) {
    console.log($("#input-time-range-hour-countermeasure").val());
    let selectedHour = parseInt($("#input-time-range-hour-countermeasure").val());
    startHourTimeCountermeasure = (selectedHour - 1) * 3600;
});


$("#input-time-range-minute-countermeasure").on('changed.bs.select', function (e) {
    console.log($("#input-time-range-minute-countermeasure").val());
    let selectedMinute = parseInt($("#input-time-range-minute-countermeasure").val());
    startMinuteTimeCountermeasure = (selectedMinute - 1) * 60;
});