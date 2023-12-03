<template>
    <el-scrollbar >
        <el-timeline>
            <el-timeline-item 
                v-for="(item, index) in reports"
                :key="index"
                :timestamp="item.time" 
                timestamp-color="rgb(209, 218, 225)"
                size="large"
                color="rgb(209, 218, 225)"
                placement="top"
                class="report-item-animation"
            >
                <el-card
                @mouseenter="showHoverContent(item.reportId)"
                @mouseleave="hideHoverContent()"
                >
                <h4>{{ item.title }}</h4>
                <p>{{ item.contains }}</p>
                </el-card>
            </el-timeline-item>
        </el-timeline>
    </el-scrollbar>
    <!-- <el-card v-if="hoverContent.show" class="timeline-detail">
        <h1>{{ timeTree[hoverContent.id - 1].eventTitle }}</h1>
        <h3>{{ timeTree[hoverContent.id - 1].eventContains }}</h3>
        <h3>{{ timeTree[hoverContent.id - 1].eventTime }}</h3>
    </el-card> -->
</template>

<script>
export default {
    data(){
        return{
            reports: [
                {
                    reportId: 1,
                    title: "不符合常规",
                    contains: "违法违规",
                    time: "2222-22-20",
                },
                {
                    reportId: 1,
                    title: "不符合常规",
                    contains: "违法违规",
                    time: "2222-22-21",
                },
                {
                    reportId: 1,
                    title: "不符合常规",
                    contains: "违法违规",
                    time: "2222-22-22",
                },
                {
                    reportId: 1,
                    title: "不符合常规",
                    contains: "违法违规",
                    time: "2222-22-23",
                },
            ],
            hoverContent: {
                show: false,
                id: -1,
            },
        }
    },
    methods:{
        showHoverContent(id) {
            console.log("showHoverContent" + id);
            this.hoverContent.show = true;
            this.hoverContent.id = id;
        },
        hideHoverContent() {
            this.hoverContent.show = false;
            this.hoverContent.id = -1;
        },
    }
}
</script>

<style scoped>
.report-item-animation {
    margin-left: 0;
  opacity: 0; /* 初始时设置不可见 */
  transform: translateY(20px); /* 初始位置在下方 */

  /* 使用过渡实现渐变显示和位置移动动画 */
  transition: opacity 5s ease, transform 5s ease;

  /* 使用 @keyframes 定义动画 */
  animation: reportItemShow 3s ease forwards;
}

@keyframes reportItemShow {
  from {
    opacity: 0; /* 初始不可见 */
    transform: translateY(20px); /* 初始位置在下方 */
  }
  to {
    opacity: 1; /* 最终可见 */
    transform: translateY(0); /* 最终位置在原始位置 */
  }
}

</style>