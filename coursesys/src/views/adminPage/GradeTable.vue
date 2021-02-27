<template>
    <div style="text-align: center">
        <div id="chart" style="width: 600px;height:400px;display: inline-block"></div>
    </div>
</template>

<script>
    export default {
        data() {
            return {
                courseNames: [],
                grades: []
            }
        },
        methods: {
            myEcharts() {
                // 基于准备好的dom，初始化echarts实例
                var myChart = this.$echarts.init(document.getElementById('chart'));

                // 指定图表的配置项和数据
                var option = {

                    title: {
                        text: '各课程平均成绩'
                    },
                    tooltip: {},
                    legend: {
                        data: ['平均成绩']
                    },
                    xAxis: {
                        data: this.courseNames
                    },
                    yAxis: {},
                    series: [{
                        itemStyle: {
                            normal: {
                                color: function (params) {
                                    var colorList = ['#FF0000', '#00FF00', '#0000FF',
                                            '#FFFF00', '#00FFFF', '#FF00FF', '#C0C0C0',
                                            '#808080', '#800000', '#808000', '#008000',
                                            '#800080', '#008080', '#000080'
                                        ],
                                        col = [];
                                    for (var i = 0; i < option.series[0].data.length; i++)
                                        col[i] = parseInt(option.series[0].data[i] / 10);
                                    return colorList[col[params.dataIndex]];
                                }
                            },
                        },
                        name: '成绩',
                        type: 'bar',
                        data: this.grades
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }
        },
        beforeMount() {
            this.postRequest("/getAllAvrGrade", null).then(resp => {
                if (resp.code == 200) {
                    this.courseNames = resp.obj.courseNames
                    this.grades = resp.obj.grades
                }
                this.myEcharts();
            })
        },
        return: "GradeTable"
    }
</script>

<style>

</style>