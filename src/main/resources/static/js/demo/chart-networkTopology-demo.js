//初始化图表
let myNetworkTopologyChart = echarts.init(document.getElementById("myNetworkTopology"));
let nodes = [
    {
        x: '4',
        y: '1',
        name: 'Firewall',
        img: 'firewall.png',
        id: '1.1.1.1'
    },
    {
        x: '2',
        y: '3',
        name: 'Router1',
        img: 'router.png',
        id: '1.1.1.2'
    },
    {
        x: '6',
        y: '3',
        name: 'Router2',
        img: 'router.png',
        id: '1.1.1.3'
    },
    {
        x: '1',
        y: '9',
        name: 'NE1',
        img: 'user.png',
        id: '59.166.0.9'
    },
    {
        x: '0',
        y: '8',
        name: 'NE2',
        img: 'user.png',
        id: '59.166.0.2'
    },
    {
        x: '0',
        y: '5',
        name: 'NE3',
        img: 'user.png',
        id: '59.166.0.3'
    },
    {
        x: '0',
        y: '2',
        name: 'NE4',
        img: 'user.png',
        id: '59.166.0.4'
    },
    {
        x: '2',
        y: '9',
        name: 'ME0',
        img: 'user.png',
        id: '175.45.176.4'
    },
    {
        x: '3',
        y: '9',
        name: 'ME1',
        img: 'user.png',
        id: '175.45.176.9'
    },
    {
        x: '4',
        y: '9',
        name: 'ME2',
        img: 'user.png',
        id: '175.45.176.2'
    },
    {
        x: '5',
        y: '9',
        name: 'ME3',
        img: 'user.png',
        id: '175.45.176.3'
    },
    {
        x: '6',
        y: '9',
        name: 'TE0',
        img: 'server.png',
        id: '149.171.126.4'
    }, {
        x: '7',
        y: '6',
        name: 'TE1',
        img: 'server.png',
        id: '149.171.126.9'
    }, {
        x: '7',
        y: '2',
        name: 'TE2',
        img: 'server.png',
        id: '149.171.126.2'
    }
];
//设备之间的连线
let links = [
    {
        source: '1.1.1.2',
        target: '1.1.1.1',
        name: '数据传输'
    }, {
        source: '1.1.1.1',
        target: '1.1.1.3',
        name: '数据传输'
    },
    {
        source: '175.45.176.4',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '175.45.176.9',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '175.45.176.2',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '175.45.176.3',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '59.166.0.9',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '1.1.1.3',
        target: '149.171.126.4',
        name: '数据传输'
    },
    {
        source: '59.166.0.2',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '59.166.0.3',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '59.166.0.4',
        target: '1.1.1.2',
        name: '数据传输'
    },
    {
        source: '1.1.1.3',
        target: '149.171.126.9',
        name: '数据传输'
    },
    {
        source: '1.1.1.3',
        target: '149.171.126.2',
        name: '数据传输'
    },
];
//网络拓扑图
let charts = {
    nodes: [],
    links: [],
    linesData: []
};
var path = [
    [6, 0, 1, 7],
    [2, 0, 1, 7],
    [3, 0, 1, 7],
    [4, 0, 1, 7],
    [5, 0, 1, 7],
    [7, 1, 0, 6],
    [7, 1, 0, 2]
];
//定义节点id位置键值对
var dataMap = new Map();
//遍历节点
for (var j = 0; j < nodes.length; j++) {
    var x = parseInt(nodes[j].x);
    var y = parseInt(nodes[j].y);
    //补充节点在图中的信息
    var node = {
        name: nodes[j].name,
        id: nodes[j].id,
        value: [x, y],
        symbolSize: 50,
        // alarm: nodes[j].alarm,
        symbol: 'image:../../img/img-networkTopology/' + nodes[j].img,//此处的图片位置是以根目录作为起始位置来算的
        itemStyle: {
            normal: {
                color: '#12b5d0',
            }
        }
    };
    //设置节点id和位置的键值对
    dataMap.set(nodes[j].id, [x, y]);
    //拓扑图中增加该节点
    charts.nodes.push(node)
}
//遍历连接
for (var i = 0; i < links.length; i++) {
    var link = {
        source: links[i].source,
        target: links[i].target,
        label: {
            normal: {
                show: true,
                formatter: '' //连接上没有对这次连接的描述
            }
        },
        lineStyle: {
            normal: {
                //修改连线的颜色可以在这里
                color: '#000000'
            }
        },
    };
    charts.links.push(link);
    //将连接转化为连接线
    var lines = [{
        coord: dataMap.get(links[i].source)
    }, {
        coord: dataMap.get(links[i].target)
    }];
    charts.linesData.push(lines)
}

let optionNetworkTopology = {
    xAxis: {
        show: false,
        type: 'value'
    },
    yAxis: {
        show: false,
        type: 'value'
    },
    tooltip: {//在鼠标悬停到图标上的时候会显示设备的信息
        formatter: function (params, ticket, callback) {
            var showContent = params.data.name + "：" + params.data.id;
            if (params.seriesIndex > 1) {
                return showContent + "%";
            }
            return showContent;
        }
    },
    series: [{
        type: 'graph',
        layout: 'none',

        focusNodeAdjacency: true,
        coordinateSystem: 'cartesian2d',
        symbolSize: 50,
        label: {
            normal: {
                show: true,
                position: 'bottom',
                color: '#000000'
            }
        },
        lineStyle: {
            normal: {
                width: 2,
                shadowColor: 'none'
            }
        },
        edgeSymbolSize: 8,
        data: charts.nodes,
        links: charts.links,
        itemStyle: {
            normal: {
                label: {
                    show: true,
                    formatter: function (item) {
                        return item.data.name
                    }
                }
            }
        },
        animation: false
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[0]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[1]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[2]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[3]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[4]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[5]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[6]]
    }, {
        name: 'A',
        type: 'lines',
        coordinateSystem: 'cartesian2d',
        polyline: true,
        effect: {
            show: true,
            trailLength: 0,
            symbol: 'rect',
            color: '#1cc88a',
            symbolSize: 8,
            animation: false
        },
        lineStyle: {
            opacity: 0,
        },
        data: [charts.linesData[7]]
    }]
};

//此处为更改颜色的函数，用了Promise来确保完成后返回
let changeTopologyColor = function (colorIndex, duration) {//color="#FF0033"(红)或者color="#1cc88a"
    var color = ["#FF0033", "#1cc88a"];
    var p = new Promise(function (resolve, reject) {
        /*for (let i = 0; i < 8; i++) {
            optionNetworkTopology.series[i + 1].effect.color = color[colorIndex];
        }
        myNetworkTopologyChart.setOption(optionNetworkTopology);*/
        for (let i = 0; i < 8; i++) {
            optionNetworkTopology.series[i + 1].effect.color = color[colorIndex];
        }
        myNetworkTopologyChart.setOption(optionNetworkTopology);
        setTimeout(function () {
            resolve(duration);
        }, duration);
    });
    return p;
};

var durations = [500, 1000, 1500, 2000, 2500, 3000, 3500, 4000, 4500, 5000];
var changeColorTest = function (colorIndex, durationIndex, number) {
    if (number === 0) {
        console.log(colorIndex ? '绿' : '红');
        changeTopologyColor(colorIndex, durations[durationIndex]);
        return;
    }
    console.log(colorIndex ? '绿' : '红');
    changeTopologyColor(colorIndex, durations[durationIndex]).then(function (val) {
        console.log(val);
        changeColorTest(1 - colorIndex, durationIndex + 1, number - 1);
    });
};
changeColorTest(0, 0, 10);


// 使用刚指定的配置项和数据显示图表
myNetworkTopologyChart.setOption(optionNetworkTopology);

window.addEventListener("resize", () => {
    myNetworkTopologyChart.resize();
});