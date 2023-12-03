<script>
export default{
    data(){
        return{
            reports:[
                {
                    reportId:1,
                    title:"不符合常规",
                    contains:"违法违规",
                    time:"2222-22-20",
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
        directToSubsystem(mode) {
            if (mode == 0) {
                this.$router.push('/adminHome');
            } else if (mode == 1) {
                this.$router.push('/userManage');
            } else if (mode == 2) {
                this.$router.push('/forumManage');
            } else if (mode == 3) {
                this.$router.push('/newsManage');
            } else if (mode == 4) {
                this.$router.push('/announceManage');
            }
        },
    }
}
</script>


<template>
    <el-row style="height: 10vh;">
        <el-col :span="24"><h1 class="welcome-text" >欢迎回来，管理员</h1></el-col>
    </el-row>
    <el-row style="height: 75vh;">
        <el-col :span="8" class="set-vertical" style="margin-top: 2vh;">
            <el-card style="margin-left: 5vw;height: 35vh;">
                <h3>
                    近30日新增用户曲线
                </h3>
            </el-card>
            <el-card style="margin-left: 5vw;margin-top: 2vh;height: 35vh;">
                <h3>
                    饼图
                </h3>
            </el-card>
        </el-col>
        <el-col :span="8" style="margin-top: 2vh;">
            <el-card style="margin-left: 1vw;margin-right: 1vw;height: 72vh;">
                <h3>
                    新增举报
                </h3>
                <el-scrollbar style="height: 60vh;">
                    <el-timeline style="margin-right: 2vw;">
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
            </el-card>
        </el-col>
        <el-col :span="8" style="margin-top: 2vh;">
            <el-card style="height: 25vh;">
                <h3 style="height: 2vh;margin-top: 0;">
                    工作台
                </h3>
                <el-container class="set-horizonal" style="height: 20vh;padding-top: 3vh;padding-left: 3vw;">
                    <el-container class="set-vertical workboard">
                        <el-icon size="3em" color="red"><User /></el-icon>
                        <h5>用户管理</h5>
                    </el-container>
                    <el-container class="set-vertical workboard">
                        <el-icon size="3em" color="blue"><ChatLineRound /></el-icon>
                        <h5>论坛管理</h5>
                    </el-container>
                    <el-container class="set-vertical workboard">
                        <el-icon size="3em" color="green"><Timer /></el-icon>
                        <h5>新闻管理</h5>
                    </el-container>
                    <el-container class="set-vertical workboard">
                        <el-icon size="3em" color="brown"><Monitor /></el-icon>
                        <h5>公告管理</h5>
                    </el-container>
                </el-container>
            </el-card>
            <el-card style="margin-top: 2vh;height: 45vh;">
                <h3>
                    发布公告
                </h3>
                <el-input
                    v-model="announcement"
                    :rows="8"
                    type="textarea"
                    placeholder="请输入公告"
                />
                <el-button type="primary" style="margin-top: 1vh;">
                    发布公告
                </el-button>
            </el-card>
        </el-col>
    </el-row>
</template>

<style lang="scss" scoped>
.set-vertical{
    display: flex;
    flex-direction: column;
}
.set-horizonal{
    display: flex;
    flex-direction: row;
}

.welcome-text {
    margin-left: 42vw;
    height: 10vh;
    margin-top: 3vh;
    font-size: 2rem;
    position: absolute;
    opacity: 0;
    animation: flyIn 1s ease forwards;
}

@keyframes flyIn {
  0% {
    transform: translateY(100%);
    opacity: 0;
  }
  100% {
    transform: translateY(0);
    opacity: 1;
  }
}

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

.workboard{
    cursor: pointer;
}
</style>