<template>
    <div>
        <div class="mainBox">
            <!-- 导航及简介 -->
            <div>
                <!-- 返回导航 -->
                <el-page-header @back="goBack()" :icon="ArrowLeft">
                    <template #content>
                        <div class="flex items-center">
                            <el-avatar :size="32" class="mr-3" :src="returnLogo(Details.matchtag)" />
                            <span style="position: relative;left:1vw;top:-1vh"> <b>{{ Details.title }}</b>
                            </span>
                        </div>
                    </template>
                    <el-descriptions :column="2" size="small" class="mt-4">
                        <el-descriptions-item label="新闻__ID：" :label-style="{ fontWeight: 300 }"><b>{{
                            Details.newsId }}</b></el-descriptions-item>
                        <el-descriptions-item label="发布时间："><b>{{ returnTime(Details.publishdatetime)
                        }}</b></el-descriptions-item>
                        <el-descriptions-item label="联赛类型："><b>{{ returnPlace(Details.matchtag)
                        }}</b></el-descriptions-item>
                        <el-descriptions-item label="新闻标签：">
                            <el-tag class="ml-2" type="warning"><b>{{ Details.matchtag }}&nbsp;</b></el-tag>
                            <el-tag class=" ml-2" type="success" style="margin-left: 5px;"><b>
                                    &nbsp;{{ Details.propertag }}&nbsp;
                                </b></el-tag>
                        </el-descriptions-item>
                    </el-descriptions>
                </el-page-header>
            </div>
            <!-- 新闻正文 -->
            <div>
                <!-- 标题 -->
                <div class="title">
                    {{ Details.title }}
                </div>
                <!-- 正文 -->
                <div class="content">
                    {{ Details.contains }}
                </div>
                <!-- 图片 -->
                <div>
                    <el-col :span="6" v-for="item in  Details.pic">
                        <img v-if="item != null"
                            referrerPolicy='no-referrer' :src="item" alt="Image" class="imgItem">
                    </el-col>
                </div>
            </div>
        </div>
    </div>
    <div class="back-to-top-container">
        <div class="back-to-top" :class="{ 'animating': isScrollingToTop }" @click="scrollToTop()"></div>
    </div>
</template>
  
<script>
import MyNav from '../../components/TopNav.vue';
import ChinaLogo from '../../assets/img/cslogo.png';
import EnglandLogo from '../../assets/img/pmlogo.png';
import SpainLogo from '../../assets/img/lllogo.png';
import GermanyLogo from '../../assets/img/bllogo.png';
import ItalyLogo from '../../assets/img/salogo.png';
import FranceLogo from '../../assets/img/le1logo.png';
import axios from "axios";

export default {
    components: {
        'my-nav': MyNav
    },

    data() {
        return {
            Details: {
                    matchtag: "",
                    propertag: "",
                    title: "",
                    summary: "",
                    contains: "",
                    news_id: 0,
                    publishDateTime: '',
                    pic: [],
            },
        };
    },
    created() {
        const id=this.$route.query.newsId;
        console.log("传入的ID值",id);
        this.getNewsDetails(id);
    },

    methods: {
        returnLogo(tag) {
            if (tag == '中超')
                return ChinaLogo;
            else if (tag == '英超')
                return EnglandLogo;
            else if (tag == '西甲')
                return SpainLogo;
            else if (tag == '德甲')
                return GermanyLogo;
            else if (tag == '意甲')
                return ItalyLogo;
            else if (tag == '法甲')
                return FranceLogo;
        },
        returnTime(time) {
            return time.replace(/T/g, '   ');
        },
        returnPlace(tag) {
            if (tag == '中超')
                return 'CSL';
            else if (tag == '英超')
                return 'Premier League';
            else if (tag == '西甲')
                return 'La Liga';
            else if (tag == '德甲')
                return 'Bundesliga';
            else if (tag == '意甲')
                return 'Serie A';
            else if (tag == '法甲')
                return 'Ligue 1';
        },

        goBack() {
            window.close();
        },

        scrollToTop() {
            // 获取元素的引用
            const container = document.documentElement
            if (container) {
                this.isScrollingToTop = true;
                const duration = 1000; // 滚动持续时间，单位：毫秒
                const startTime = performance.now();
                const startTop = container.scrollTop;
                const targetTop = 0; // 目标滚动位置（顶部）
                const animateScroll = (timestamp) => {
                    const elapsedTime = timestamp - startTime;
                    if (elapsedTime >= duration) {
                        container.scrollTop = targetTop;
                        this.isScrollingToTop = false;
                        return;
                    }
                    const easeInOutCubic = (t) => t < 0.5 ? 4 * t * t * t : (t - 1) * (2 * t - 2) * (2 * t - 2) + 1;
                    const scrollProgress = easeInOutCubic(elapsedTime / duration);
                    container.scrollTop = startTop + (targetTop - startTop) * scrollProgress;
                    requestAnimationFrame(animateScroll);
                };
                requestAnimationFrame(animateScroll);
            }
        },


      //从后端获取新闻数据
      async getNewsDetails(id) {
        try {
          const response = await axios.get('/api/news/newsdetails', {
            params: {
              id:id,
            }
          }); // 发送POST请求，并将请求数据作为 JSON 对象发送

          console.log(response);

          this.Details=response.data;

        } catch (error) {
          // 处理获取失败的情况
          this.$message.error('数据获取失败，请重试！');
        }

      }
    },
};
</script>

<style scoped>
.mainBox {
    width: 60vw;
    position: relative;
    left: 20vw;
    top: 2vh;
}

.title {
    position: relative;
    top: 2vh;
    left: 0;
    flex-shrink: 0;
    color: #000;
    font-family: FZYaoTi;
    font-size: 30px;
    font-style: normal;
    font-weight: 700;
    line-height: normal;
}

.content {
    position: relative;
    top: 4vh;
    font-family: FZSongTi;
    font-size: 18px;
    font-style: normal;
    font-weight: 500;
    line-height: 30px;
    /* line-height: 300%; */
}

.imgItem {
    position: relative;
    top: 6vh;
    left: 0;
    border-radius: 6px;
    width: 50vw;
}

.back-to-top-container {
    display: flex;
    /* position: relative; */
    width: 300px;
    height: 80vh;
    position: fixed;
    top: 0;
    left: 80vw;
}

.back-to-top {
    cursor: pointer;
    top: 0;
    width: 70px;
    height: 590px;
    margin-left: 35%;
    background: url(../../assets/img/scroll_cat.png);
    transition: transform 0.5s ease-in-out, opacity 0.5s ease-in-out;
    opacity: 1;
}

.back-to-top.animating {
    transform: translateY(-200px);
    opacity: 1;
}
</style>