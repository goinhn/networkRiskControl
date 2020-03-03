//初始化图表
let mySunburstChart = echarts.init(document.getElementById("mySunburstChart"));
let optionSunburstChart = {
    series: {
        type: 'sunburst',
        data: [{
            name: 'A',
            value: 10,
            children: [{
                value: 3,
                name: 'Aa'
            }, {
                value: 5,
                name: 'Ab'
            }]
        }, {
            name: 'B',
            children: [{
                name: 'Ba',
                value: 4
            }, {
                name: 'Bb',
                value: 2
            }]
        }, {
            name: 'C',
            value: 3
        }]
    }
};
mySunburstChart.setOption(optionSunburstChart);

window.addEventListener("resize", () => {
    mySunburstChart.resize();
});