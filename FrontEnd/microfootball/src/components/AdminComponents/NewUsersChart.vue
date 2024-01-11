<script>
import axios from "axios";
import * as echarts from 'echarts';
export default {
    data() {
        return {
            sysData:{},
            barChartOptions: {},
        }
    },
    methods: {
        async init(){
            await this.getSysInfo();
            this.initChart();
        },
        async getSysInfo(){
            let response
            const adminToken = localStorage.getItem('adminToken');
            try {
                response = await axios.get("/api/admin/sys/sysinfo", {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        'token': adminToken
                    },
                });
                console.log(response)
                if (response.status == 200) {
                    this.sysData=response.data.data
                    
                }
            } catch (error) {
                console.log(error)
            }
        },
        initChart() {
            // 获取容器元素
            const chartContainer = this.$el.querySelector('.chart-container');

            // 创建图表实例
            const myChart = echarts.init(chartContainer);

            // 配置图表选项
            const options = {
                title: {
                    text: '昨日新增数据',
                },
                xAxis: {
                    type: 'category',
                    data: ['用户', '帖子', '新闻', '公告'],
                },
                yAxis: {
                    type: 'value',
                },
                series: [
                    {
                        name: '数据',
                        type: 'bar',
                         data: [
                            this.sysData.newUserNum,
                            this.sysData.newPostNum,
                            this.sysData.newNewsNum,
                            this.sysData.newAnnounceNum,
                        ],
                    },
                ],
            };

            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(options);
        },
    },
    mounted(){
        this.init();
    }
}
</script>

<template>
    <el-card>
        <!-- <h3>
            昨日系统信息
        </h3>
        <el-container class="sub-box" style="background-color: rgb(246, 148, 148);">
            <span class="sub-text">昨日新增用户：{{ sysData.newUserNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(156, 233, 49);">
            <span class="sub-text">昨日新增帖子：{{ sysData.newPostNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(148, 174, 246);">
            <span class="sub-text">昨日新增新闻：{{ sysData.newNewsNum }}</span>
        </el-container>
        <el-container class="sub-box" style="background-color: rgb(217, 246, 148);">
            <span class="sub-text">昨日新增公告：{{ sysData.newAnnounceNum }}</span>
        </el-container> -->
        <div class="chart-container"></div>
    </el-card>
</template>

<style scoped>
.sub-box {
  position: relative;
  border-radius: 10px;
  height: 7vh;
  width: 100%;
  margin-top:15px;
}

.sub-text {
  margin-top: 2vh;
  margin-left: 5vw;
  height: 1.5vh;
  color: rgb(54, 46, 46);
  font-weight: bold;
}

.chart-container {
  width: 100%;
  height: 300px;
}
</style>