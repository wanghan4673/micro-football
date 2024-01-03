<template>
  <!-- 顶部导航栏 -->
  <!--  <my-nav></my-nav>-->
  <div class="common-layout">
    <el-container>
      <el-header style="display: flex; justify-content: space-between;">
        <!-- 导航栏logo -->
        <div>
          <a class="logo">FootBall</a>
          <b class="title">足坛新闻</b>
        </div>
        <div style="display: flex; justify-content: space-between;">
          <!-- 搜索栏 -->
          <el-input style="margin-left: 20px;" class="search" v-model="searchInput" placeholder=" 请 输 入 搜 索 关 键 字">
            <template v-slot:prepend>
              <el-button icon="Search" @click="search"></el-button>
            </template>
          </el-input>
          <img src="../../assets/img/recover_logo.png" class="img">
          <img src="../../assets/img/football_logo.png" class="img">
        </div>
      </el-header>
<!--      <div class="line" style="left:1%; width: 98%;height: 3px;"></div>-->
      <!-- 新闻导航栏 -->
      <el-main>
        <el-menu mode="horizontal" active-text-color="#409eff">
          <el-menu-item @click="activeTab = 'home';resetData() " :class="{ 'active': activeTab == 'home' }"
            class="menu">首页</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'China'; switchLeague('中超') }"
            :class="{ 'active': activeTab == 'China' }" class="menu">中超</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'England'; switchLeague('英超') }"
            :class="{ 'active': activeTab == 'England' }" class="menu">英超</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'Spain'; switchLeague('西甲') }"
            :class="{ 'active': activeTab == 'Spain' }" class="menu">西甲</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'Germany'; switchLeague('德甲') }"
            :class="{ 'active': activeTab == 'Germany' }" class="menu">德甲</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'Italy'; switchLeague('意甲') }"
            :class="{ 'active': activeTab == 'Italy' }" class="menu">意甲</el-menu-item>
          <el-menu-item @click="() => { activeTab = 'France'; switchLeague('法甲') }"
            :class="{ 'active': activeTab == 'France' }" class="menu">法甲</el-menu-item>
        </el-menu>
      </el-main>
    </el-container>
  </div>
  <div style="width: 100%; border: 0px solid #000; display: flex;flex-direction: column; align-items: center;">
     <div class="line" style="left:0; width: 100%;height: 0.05px;top:-12vh"></div>
    <div style="width: 100%; display: flex;flex-direction: column; padding: 20px;">
      <!-- 首页 -->
      <el-container style="" v-if="activeTab == 'home'">
        <!-- 左侧部分 -->
        <el-aside style="width: 30%; border-right: 1px solid #24242431;">
          <!-- 走马灯 -->
          <div style="border: 1px solid #24242431;">
            <el-carousel :interval="3000">
              <el-carousel-item v-if="carouselRecommendItems.length != 0" v-for="(item, index) in carouselRecommendItems"
                :key="index">
                <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                  :src="item.pic[0]" alt="carousel image" style="width: 100%;height: 100%;"
                  @click="openNewsDetails(item.newsId)">
                <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                  alt="Image" class="imgANO" @click="openNewsDetails(item.newsId)">
                <div style="background-color: rgb(0, 0, 0.5); color: white; position: relative;bottom: 25px;"
                  @click="openNewsDetails(item.newsId)">{{
                    truncateText(truncateText(item.title, 16), 16) }}</div>
              </el-carousel-item>
            </el-carousel>
          </div>
          <!-- 推荐模块展示内容（配图） -->
          <div class="imgList1">
            <el-row>
              <el-col :span="12" v-if="recommendItems1.length != 0" v-for="(item, index) in recommendItems1" :key="index">
                <div class="imgItem1" @click="openNewsDetails(item.newsId)">
                  <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                    :src="item.pic[0]" alt="Image" class="imgRecommend" @click="openNewsDetails(item.newsId)">
                  <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                    alt="Image" class="imgRecommend" @click="openNewsDetails(item.newsId)">
                  <div class="descriptionRecommend" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 16) }}
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>
          <!-- 热门视频部分 -->
          <div style="border: 1px solid #24242431;">
            <div style="display: flex;justify-content: space-between;padding: 10px;">
              <p class="titleLeft">热门视频</p>
              <p class="moreLeft" @click="openVideoMore()">更多 ></p>
            </div>
            <div>
              <!-- 走马灯 -->
              <el-carousel :interval="3000">
                <el-carousel-item v-if="recommendItems1.length != 0" v-for="(item, index) in carouselVideoItems"
                  :key="index">
                  <img referrerPolicy='no-referrer' :src="item.cover" alt="carousel image"
                    @click="openLink(item.urllink)" class="LeftCarousel">
                  <div style="background-color: rgb(0, 0, 0.5); color: white; position: relative;bottom: 20px;"
                    @click="openLink(item.urllink)">
                    {{ truncateText(item.title, 16) }}
                  </div>
                </el-carousel-item>
              </el-carousel>
            </div>

            <!-- 热门视频模块展示内容（配图） -->
            <div class="imgList1" style="top:-8vh">
              <el-row>
                <el-col :span="12" v-if="recommendItems1.length != 0" v-for="(item, index) in videoItems1" :key="index">
                  <div class="imgItem1">
                    <img referrerPolicy='no-referrer' :src="item.cover" alt="Image" class="imgRecommend"
                      @click="openLink(item.urllink)">
                    <div class="descriptionRecommend" @click="openLink(item.urllink)">{{ truncateText(item.title,
                      16) }}</div>
                  </div>
                </el-col>
              </el-row>
            </div>

            <!-- 热点视频模块展示内容（文字标题） -->
            <div class="videoText">
              <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in videoItems2" :key="index">
                <div class="textItem">
                  <el-icon style="font-size: 23px;" @click="openLink(item.urllink)">
                    <VideoPlay />
                  </el-icon>
                  <a class="Text" @click="openLink(item.urllink)">{{ truncateText(item.title, 16) }}</a>
                </div>
              </el-row>
            </div>
          </div>

        </el-aside>
        <!-- 中间和右侧部分 -->
        <el-main width="70vw">
          <div class="common-layout">
            <el-container>
              <!-- 中间部分 -->
              <el-aside width="30vw">
                <div class="center">
                  <!-- 热门推荐部分 -->
                  <div class="recommendText">
                    <p class="CenterMainTitle">热门推荐</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in recommendItems2" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 26) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 中超新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">中超</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in ChinaItems" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 26) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 英超新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">英超</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in ENGLANDItems" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 28) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 西甲新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">西甲</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in SpainItems" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 28) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 德甲新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">德甲</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in GermanyItems" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 26) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 意甲新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">意甲</p>
                    <el-row v-if="recommendItems1.length != 0" v-for="(item, index) in ItalyItems" :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 26) }}</a>
                      </div>
                    </el-row>
                  </div>
                  <!-- 法甲新闻 -->
                  <div class="CenterNews">
                    <p class="CenterTitle">法甲</p>
                    <el-row v-if="FranceItems.length != 0" v-for="( item, index ) in  FranceItems " :key="index">
                      <div class="textItem" @click="openNewsDetails(item.newsId)">
                        <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                          <Football />
                        </el-icon>
                        <a class="Text" @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 26) }}</a>
                      </div>
                    </el-row>
                  </div>
                </div>

              </el-aside>

              <!-- 右侧部分 -->
              <el-main class="Right">
                <div style="display: flex;justify-content: space-between;padding: 10px;">
                  <p class="titleLeft">球坛八卦</p>
                  <p class="moreLeft" @click="openGossipMore()">更多 ></p>
                </div>
                <div>
                  <!-- 走马灯 -->
                  <el-carousel :interval="3000">
                    <el-carousel-item v-if="recommendItems1.length != 0" v-for="(item, index) in carouselGossipItems"
                      :key="index">
                      <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                        :src="item.pic[0]" alt="carousel image" @click="openNewsDetails(item.newsId)" class="imgANO">
                      <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                        alt="Image" @click="openNewsDetails(item.newsId)" class="imgANO">
                      <div style="background-color: rgb(0, 0, 0.5); color: white; position: relative;bottom: 20px;"
                        @click="openNewsDetails(item.newsId)">{{ truncateText(item.title, 16)
                        }}
                      </div>
                    </el-carousel-item>
                  </el-carousel>
                </div>
                <!-- 图文八卦新闻 -->
                <div>
                  <div v-if="recommendItems1.length != 0" v-for="(item, index) in  gossipItems1 " :key="index"
                    style="padding-top: 10px;">
                    <el-row :gutter="20">
                      <el-col :span="11">
                        <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                          :src="item.pic[0]" alt="GossipImage1" class="imgGossip" @click="openNewsDetails(item.newsId)">
                        <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                          alt="GossipImage2" class="imgGossip" @click="openNewsDetails(item.newsId)">
                      </el-col>
                      <el-col :span="8">
                        <div v-if="item != null" class="descriptionGossip" @click="openNewsDetails(item.newsId)">{{
                          truncateText(item.title,
                            25) }}</div>
                      </el-col>
                    </el-row>
                  </div>
                </div>
                <!-- 八卦推荐部分 -->
                <div>
                  <el-row v-if="gossipItems2.length != 0" v-for="( item, index ) in  gossipItems2 " :key="index">
                    <div class="textItem">
                      <el-icon style="font-size: 23px;" @click="openNewsDetails(item.newsId)">
                        <ToiletPaper />
                      </el-icon>
                      <a v-if="item != null" class="Text" @click="openNewsDetails(item.newsId)">{{
                        truncateText(item.title, 16) }}</a>
                    </div>
                  </el-row>
                </div>
              </el-main>

            </el-container>
          </div>
        </el-main>

      </el-container>

      <!-- 联赛选择 -->
      <el-container style="top:22vh;position: absolute;" v-if="activeTab != 'home' && activeTab != 'Search'">
        <div class="containerLeague">
          <el-row>
            <!-- 左侧动态 -->
            <el-col :span="18" class="leftLeague">
              <p class="titleLeagueLeft">{{ LeagueTitle1 }}</p>
<!--              <div class="line" style="width: 40vw;height: 0.2px;top:-3vh;left:5vw;"></div>-->
              <div v-if="LeagueNews.length != 0" v-for="(item, index) in LeagueNews" :key="index" class="itemLeague">
                <div class="imgWrapper" @click="openNewsDetails(item.newsId)">
                  <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                    :src="item.pic[0]" alt="AssistImage1" class="imgSearch">
                  <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                    alt="AssistImage2" class="imgSearch">
                </div>
                <div class="TextWrapper" @click="openNewsDetails(item.newsId)" style="left:-8vw;">
                  <div class="titleSearch">{{ truncateText(item.title, 20) }}</div>
                  <div class="descriptionSearch">{{ item.summary }}</div>
                </div>
              </div>
              <div class="NoMore">No More ......</div>
            </el-col>
            <!-- 视频（位于右侧） -->
            <el-col :span="6">
              <p class="titleRight" style="font-size: 25px; top:7vh">联赛视频</p>
              <el-icon class="iconSearch" style="top:2.5vh;left:12vw;">
                <VideoPlay />
              </el-icon>
<!--              <div class="line" style="width: 4vw;height: 0.2px;top:-5vh;left:-3%;"></div>-->
              <div class="imgListSearch" style="top:0vh">
                <div v-if="LeagueVideo.length != 0" v-for="( item, index ) in  LeagueVideo " :key="index"
                  class="imgItemSearch">
                  <el-row :gutter="20">
                    <el-col :span="11">
                      <img v-if="item.cover != null" referrerPolicy='no-referrer' :src="item.cover" alt="Image"
                        class="imgVideoSearch" @click="openLink(item.urllink)">
                    </el-col>
                    <el-col :span="8">
                      <div v-if="item.title != null" class="descriptionVideoSearch" @click="openLink(item.urllink)">{{
                        truncateText(item.title, 20) }}
                      </div>
                    </el-col>
                  </el-row>
                </div>
              </div>
              <div class="NoMore" style="top:-6vh">No More ......</div>
            </el-col>

          </el-row>
        </div>
      </el-container>

      <!-- 搜索结果 -->
      <el-container style="top:22vh;position: absolute;" v-if="activeTab == 'Search'">
        <el-row>
          <!-- 搜索新闻结果（位于左侧） -->
          <p class="titleLeagueLeft" style="left:3vw;">搜索结果</p>
          <div class="line" style="width: 40vw;height: 0.2px;top:4vh;left:-24vw;"></div>
          <el-col :span="18">
            <div v-for="item  in  searchNewsResults " :key="item.id" class="itemSearch" style="top:5vh">
              <div class="imgWrapper" style=" position: relative;top: -10vh;left: -4vw;" @click="openNewsDetails(item.newsId)">
                <img v-if="item.pic != ''" referrerPolicy='no-referrer'
                  :src="item.pic[0]" alt="SearchImage" class="imgSearch">
                <img v-else referrerPolicy='no-referrer' src="https://football-1316860845.cos.ap-shanghai.myqcloud.com/home_slider2.jpg"
                  alt="SearchImage" class="imgSearch">
              </div>
              <div class="TextWrapper" @click="openNewsDetails(item.newsId)">
                <div v-if="item != null" class="titleSearch" style="top:-9vh;left:-11vw;">{{
                  truncateText(item.title, 20) }}
                </div>
                <div v-if="item != null" class="descriptionSearch" style="top:-6vh;left:-11vw;">{{
                  truncateText(item.summary, 80) }}</div>
              </div>
            </div>
            <div class="NoMore">No More ......</div>
          </el-col>
          <!-- 搜索视频结果（位于右侧） -->
          <el-col :span="6">
            <p class="titleRight" style="font-size: 25px; top:-10vh">搜索视频</p>
            <el-icon class="iconSearch">
              <VideoPlay />
            </el-icon>
            <div class="line" style="width: 22vw;height: 0.2px;top:-15vh;left:-3%;"></div>
            <div class="imgListSearch" style="top:-17vh">
              <div v-if="searchVideoResults.length != 0" v-for="( item, index ) in  searchVideoResults " :key="index"
                class="imgItemSearch">
                <el-row :gutter="20">
                  <el-col :span="11">
                    <img v-if="item.cover != null" referrerPolicy='no-referrer' :src="item.cover" alt="Image"
                      class="imgVideoSearch" @click="openLink(item.urllink)">
                  </el-col>
                  <el-col :span="8">
                    <div v-if="item.title != null" class="descriptionVideoSearch" @click="openLink(item.urllink)">{{
                      truncateText(item.title, 20) }}
                    </div>
                  </el-col>
                </el-row>
              </div>
            </div>
            <div class="NoMore" style="top:-6vh">No More ......</div>
          </el-col>
        </el-row>
      </el-container>
    </div>
<!--    <div class="line" style="left:5%; width: 80%;height: 0.05px;top:-20vh"></div>-->
  </div>
  <el-backtop :right="100" :bottom="100" />
</template>

<script>
import MyNav from '../../components/TopNav.vue';
import { ElInput, useTransitionFallthroughEmits } from 'element-plus';
import { ElCarousel, ElCarouselItem } from 'element-plus';
import { ElMessage } from 'element-plus';
import axios from 'axios';
import NewsDetails from './NewsDetails.vue';

export default {
  components: {
    'my-nav': MyNav,
    'newsdetails': NewsDetails,
    ElCarousel,
    ElCarouselItem
  },
  data() {
    return {
      ok: '',

      //当前联赛
      LeagueTitle1: '',  //**动态
      LeagueTitle2: '',  //**球队一览

      activeTab: 'home', // 新闻导航栏默认选中的选项
      isFixed: false,    //用于判断联赛界面的右侧悬浮

      //以下为搜索数据
      searchInput: '',    //搜索框的内容
      searchNewsResults: [],    //搜索结果
      searchVideoResults: [],   //搜索结果

      //以下为主页面所需数据
      carouselRecommendItems: [],  //推荐模块走马灯显示图片及内容
      carouselVideoItems: [],   //热门视频走马灯显示图片及内容
      carouselGossipItems: [],  //八卦走马灯显示图片及内容
      recommendItems1: [],  //推荐模块展示内容（配图）
      recommendItems2: [], //推荐模块展示内容（不配图）
      videoItems1: [], //视频模块展示内容（配图）
      videoItems2: [],  //视频模块展示内容（不配图）
      gossipItems1: [], //八卦模块展示内容（配图）
      gossipItems2: [], //八卦模块展示内容（不配图）
      ChinaItems: [],//中超模块展示内容
      ENGLANDItems: [],  //英超模块展示内容
      FranceItems: [],//法甲模块展示内容
      ItalyItems: [], //意甲模块展示内容
      GermanyItems: [], //德甲模块展示内容
      SpainItems: [],  //西甲模块展示内容

      //以下为选择联赛后所需数据
      LeagueNews: [],    //联赛模块展示新闻
      LeagueTeam: [],//联赛队伍队徽
      LeagueShooter: [],   //联赛队伍射手榜
      LeagueVideo: [],
    }
  },

  //进入页面后调取接口，从而获得一组数据

  mounted() {
    this.initNewsData();
  },

  methods: {
    //初始化数据
    async initNewsData() {
      try {
        const response = await axios.get('/api/news/init', ''); // 发送POST请求，并将请求数据作为 JSON 对象发送

        this.carouselRecommendItems = response.data.news[0];
        this.recommendItems1 = response.data.news[1];
        this.recommendItems2 = response.data.news[2];

        this.ChinaItems = response.data.news[3];
        this.ENGLANDItems = response.data.news[4];
        this.SpainItems = response.data.news[5];
        this.GermanyItems = response.data.news[6];
        this.ItalyItems = response.data.news[7];
        this.FranceItems = response.data.news[8];

        this.carouselGossipItems = response.data.news[9];
        this.gossipItems1 = response.data.news[10];
        this.gossipItems2 = response.data.news[11];

        this.carouselVideoItems = response.data.videos[0];
        this.videoItems1 = response.data.videos[1];
        this.videoItems2 = response.data.videos[2];
      } catch (error) {
        // console.error(error);
        // 处理获取失败的情况
        this.$message.error('数据获取失败，请重试！');
      };
    },
    resetData() {
      this.initNewsData();
      this.LeagueNews = [];
      this.LeagueTeam = [];
      this.LeagueShooter = [];
      this.LeagueVideo = [];
    },
    //搜索
    search() {
      this.activeTab = 'Search';
      // 执行搜索逻辑
      this.performSearch(String(this.searchInput));
    },
    //调用搜索接口，并更新数据
    async performSearch(keyword) {
      try {

        const response1 = await axios.get('/api/news/searchnews', {
          params: {
            SearchItem: String(keyword),
          }
        });
        // console.log('123456');
        // console.log(response1.data.value);

        const response2 = await axios.get('/api/news/searchvideo', {
          params: {
            SearchItem: String(keyword),
          }
        });

        // console.log(response2.data.value);

        // 将数组存贮于传入的数组名中
        this.searchNewsResults = response1.data;
        this.searchVideoResults = response2.data;
        console.log(this.searchNewsResults);
        console.log(this.searchVideoResults);
        console.log(this.activeTab);
      } catch (error) {
        // console.error(error);
        // 处理获取失败的情况
        this.$message.error('数据获取失败，请重试！');
      };
      return;
    },

    //联赛页面球队队徽悬挂
    handleScroll() {
      const scrollTop = window.scrollY;
      const threshold = 2000; // 右侧部分滑动到多少位置时触发悬浮效果

      this.isFixed = scrollTop >= threshold;
    },

    //打开链接的页面
    openLink(url) {
      window.open(url, '_blank');
    },
    //跳转到八卦页面
    openGossipMore() {
      // 打开新窗口
      let newPage = this.$router.resolve({
        path: 'NewsGossip',
      });
      window.open(newPage.href, '_blank');
    },
    //跳转到视频页面
    openVideoMore() {
      // 打开新窗口
      let newPage = this.$router.resolve({
        path: 'NewsVideo',
      });
      window.open(newPage.href, '_blank');
    },
    //根据联赛名称返回其int值
    returnLeagueInt(LeagueName) {
      if (LeagueName == '中超')
        return 6;
      else if (LeagueName == '英超')
        return 1;
      else if (LeagueName == '西甲')
        return 2;
      else if (LeagueName == '意甲')
        return 3;
      else if (LeagueName == '德甲')
        return 4;
      else if (LeagueName == '法甲')
        return 5;
    },

    //从后端获取新闻数据
    async getData(newsQuantity, tag1, tag2, dataItems) {
      try {
        const response = await axios.get('/api/news/news', {
          params: {
            Tag1: String(tag1),
            Tag2: String(tag2),
            num: newsQuantity
          }
        }); // 发送POST请求，并将请求数据作为 JSON 对象发送

        // 将数组存贮于传入的数组名中
        dataItems.splice(0, dataItems.length, ...response.data);

      } catch (error) {
        // 处理获取失败的情况
        this.$message.error('数据获取失败，请重试！');
      }

    },
    //从后端获取视频数据
    async getVideoData(newsQuantity, tag1, tag2, dataItems) {
      try {
        const response = await axios.get('/api/news/video', {
          params: {
            Tag1: String(tag1),
            Tag2: String(tag2),
            num: newsQuantity
          }
        }); // 发送POST请求，并将请求数据作为 JSON 对象发送

        // console.log(response.data.value);

        // 将数组存贮于传入的数组名中
        dataItems.splice(0, dataItems.length, ...response.data);
        // console.log(dataItems);
        // console.log(this.items);
      } catch (error) {
        // console.error(error);
        // 处理获取失败的情况
        this.$message.error('数据获取失败，请重试！');
      }

    },

    //截断过长的文本
    truncateText(text, maxLength) {
      if (text.length > maxLength) {
        return text.substring(0, maxLength) + '...';
      }
      return text;
    },

    //切换联赛展示
    switchLeague(gameType) {
      this.carouselRecommendItems = []
      this.carouselVideoItems= []
      this.carouselGossipItems= []
      this.recommendItems1= []
      this.recommendItems2= []
      this.videoItems1= []
      this.videoItems2= []
      this.gossipItems1= []
      this.gossipItems2= []
      this.ChinaItems= []
      this.ENGLANDItems= []
      this.FranceItems= []
      this.ItalyItems= []
      this.GermanyItems= []
      this.SpainItems= []

      this.LeagueTitle1 = gameType + '动态';
      this.LeagueTitle2 = gameType + '球队一览';
      //更改联赛数组

      this.getData(-1, gameType, '', this.LeagueNews);

      //this.getTeamName(this.returnLeagueInt(gameType));
      //this.getShooter(gameType);
      this.getVideoData(-1, gameType, '', this.LeagueVideo);

      console.log("abcdddd",this.LeagueNews);
    },


    //打开新闻详情页
    openNewsDetails(newsId) {
      // 打开新窗口
      let newPage = this.$router.resolve({
        path: 'NewsDetails',
        query: { newsId: newsId },
      });
      window.open(newPage.href, '_blank');
    },

  }
}
</script>

<style  scoped>
.line {
  background: #000000;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25) inset;
  position: relative;
}

.logo {
  position: relative;
  top: 10px;
  /* left: 2%; */
  flex-shrink: 0;
  color: #000;
  text-align: center;
  font-family: 'Trebuchet MS', 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
  font-size: xx-large;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
}

.title {
  position: relative;
  top: 10px;
  flex-shrink: 0;
  color: #000;
  text-align: center;
  font-family: FZYaoTi;
  font-size: xx-large;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
}

.search {
  position: relative;
  top: 10px;
  /* left: 45%; */
  width: 420px;
  height: 45px;
  flex-shrink: 0;
  border-radius: 5px;
  background: #f8f5f5;
  border: 0;
  font-size: 15px;
}

.menu {
  position: relative;
  top: -1vh;
  left: 1%;
  color: #000;
  text-align: center;
  font-family: Microsoft YaHei;
  font-size: 20px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
}

.img {
  top: 2vh;
  width: 35px;
  height: 35px;
  position: relative;
}

.imgANO {
  width: 100%;
  height: 100%;
}

/* <--以下是首页样式--> */

.CarouselRight {
  left: 0%;
  width: 80%;
  height: 80%;
  position: relative;
  top: 5vh;
}

/* 左上走马灯描述 */
.description {
  position: absolute;
  bottom: 6vh;
  left: 0;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 1px;
}

/* 左侧图文样式板块 */
.imgList1 {
  border: 1px solid #24242431;
  /* position: relative; */
  /* width: 80%; */
  /* left: 8%; */
  /* bottom: 6vh; */
}

.imgItem1 {
  display: flex;
  flex-direction: column;
  align-items: center;
  margin: 10px;
}

/* 右侧图文样式板块 */
.imgList2 {
  display: flex;
  flex-direction: column;
  position: relative;
  top: -7vh;
  left: -0.6vh;
}

.imgItem2 {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10px;
}

/* 左上推荐部分图片样式 */
.imgRecommend {
  top: 0vh;
  width: 12vw;
  height: 15vh;
  position: relative;
}

/* 左上推荐部分图片描述 */
.descriptionRecommend {
  width: 105%;
  height: 105%;
  flex-shrink: 0;
  color: #000;
  font-family: Mogra;
  font-size: 14px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  position: relative;
  top: 1vh;
  left: 1%
}

/* 左侧热门视频标题 */
.titleLeft {
  width: 90px;
  height: 23px;
  flex-shrink: 0;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

/* 左侧更多>跳转样式 */
.moreLeft {
  /* width: 15px; */
  height: 15px;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 10px;
  font-style: normal;
  font-weight: 290;
  line-height: normal;
  /* left: 80%; */
}

/* 右侧更多>跳转样式 */
.moreRight {
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 12px;
  font-style: normal;
  font-weight: 290;
  line-height: normal;
  position: relative;
  top: -4.2vh;
  left: 67%;
}

/* 右侧大布局 */
.Right {
  width: 30vw;
  position: relative;
  top: -2.1vh;
}

/* 右侧八卦标题 */
.titleRight {
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  position: relative;
  top: -1vh;
  left: -2.5%;
}

/* 右侧走马灯 */
.RightCarousel {
  position: relative;
  top: -7.5vh;
  left: -2%;
}

/* 右侧图文样式 */
.RightImage {
  position: relative;
  top: -6vh;
}

/* 八卦推荐部分图片描述 */
.descriptionGossip {
  width: 100%;
  height: 40%;
  /* flex-shrink: 0; */
  color: #000;
  font-family: Mogra;
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  position: relative;
  left: -5%;
  top: 0vh;
}

/* 八卦推荐部分图片样式 */
.imgGossip {
  width: 100%;
  height: 100%;
}

/* 左侧走马灯 */
.LeftCarousel {
  position: relative;
  top: -7.5vh;
  left: 8%;
}

.center {
  overflow: hidden;
}

/* 热点视频文字样式 */
.videoText {
  /* position: relative;
  left: 7%;
  top: -8vh; */
}

/* 推荐文字样式 */
.recommendText {
  position: relative;
  left: -2%;
  top: -5%;
}

.Text {
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 17px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  flex-shrink: 0;
  position: relative;
  left: 4%;
  top: -5%;
}

.textItem {
  display: flex;
  margin: 9px;
}

.CenterNews {
  position: relative;
  left: -2%;
  top: 1vh;
}

.RightNews {
  position: relative;
  left: -4%;
  top: -4vh;
}

.CenterMainTitle {
  position: relative;
  left: 2%;
  top: 0vh;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.CenterTitle {
  position: relative;
  left: 2%;
  top: 0vh;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 20px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
}

.video {
  position: relative;
  top: -3vh;
}

.logoex {
  position: relative;
  top: -2.5vh;
  left: 40%;
  font-size: 33px;
}

/* <--首页样式结束--> */


/* <--<--以下是搜索结果样式-->--> */
/* <--新闻搜索结果--> */
.itemSearch {
  display: flex;
  /* align-items: center; */
  margin-bottom: 10px;
  position: relative;
  left: 6vw;
  top: -5vh;
  margin-top: 5%;
}

.imgWrapper {
  margin-right: 10px;
  width: 60vw;
}

.TextWrapper {
  display: flex;
  flex-direction: column;
  position: relative;
  left: -3vw;
  top: 0;
}

/* 图片样式 */
.imgSearch {
  top: 2vh;
  left: 0;
  width: 30vw;
  height: 30vh;
  position: relative;
}

/* 标题样式 */
.titleSearch {
  font-weight: bold;
  width: 22vw;
  /* 设置容器的宽度 */
  overflow: hidden;
  /* 隐藏超出容器宽度的文本 */
  text-overflow: ellipsis;
  /* 使用省略号表示超出的文本 */
  /* white-space: nowrap; */
  /* white-space: nowrap;  不换行 */
  margin-top: 5px;
  font-size: 30px;
  position: relative;
  top: 2vh;
  left: -10vw;
}

/* 描述样式 */
.descriptionSearch {
  width: 20vw;
  /* 设置容器的宽度 */
  max-height: 120px;
  /* 设置最大高度 */
  overflow: hidden;
  /* 隐藏超出容器宽度的文本 */
  text-overflow: ellipsis;
  /* 使用省略号表示超出的文本 */
  margin-top: 5px;
  font-size: 17px;
  position: relative;
  left: -10vw;
  top: 4vh;
}

/* <--新闻搜索结果结束--> */

/* <--视频搜索结果--> */
.iconSearch {
  font-size: 30px;
  position: relative;
  top: -14.5vh;
  left: 90%;
}

.imgVideoSearch {
  top: 0vh;
  left: -2%;
  width: 10vw;
  height: 12vh;
  position: relative;
}

.descriptionVideoSearch {
  flex-shrink: 0;
  color: #000;
  font-family: Mogra;
  font-size: 18px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  position: relative;
  left: 2vw;
  top: 0vh;
  width: 8vw;
  /* 设置容器的宽度 */
  max-height: 80%;
  /* 设置最大高度 */
  overflow: hidden;
  /* 隐藏超出容器宽度的文本 */
  text-overflow: ellipsis;
  /* 使用省略号表示超出的文本 */
}

/* <--视频搜索结果结束--> */
.NoMore {
  color: #000;
  text-align: center;
  font-family: STSong;
  font-size: 16px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  position: relative;
  top: 5%
}

.imgListSearch {
  display: flex;
  flex-direction: column;
  position: relative;
  top: -24vh;
  left: -0.7vh;
}

.imgItemSearch {
  display: flex;
  align-items: flex-start;
  margin-bottom: 10px;
  position: relative;
  top: 3vh;
}

/* <--<--搜索结果样式结束-->--> */
/* <--<--以下是联赛样式-->--> */
.containerLeague {
  display: flex;
}

.leftLeague {
  flex: 1;
  height: 100vh;
}

.rightLeague {
  flex: 1;
  position: relative;
  /* 设置为相对定位，以便内部元素使用绝对定位 */
  height: 30vh;
  left: 18vw;
}

.fixedLeague {
  position: fixed;
  top: 0;
  left: 80vw;
}

.Top-Block {
  position: relative;
  top: 25px;
  /* 设置悬浮块的初始位置 */
  left: -13vw;
  /* 设置悬浮块的初始位置 */
}

.Down-Block {
  margin-top: 50vh;
  /* 为了避免悬浮块覆盖内容，设置内容的上边距 */
  height: 20vh;
  position: relative;
  left: -7vw;
}

.titleLeagueLeft {
  width: 364px;
  height: 76px;
  flex-shrink: 0;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 36px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  position: relative;
  left: 6vw;
  top: 5vh;
}

.itemLeague {
  display: flex;
  /* align-items: center; */
  margin-bottom: 10px;
  position: relative;
  left: 4vw;
  top: -3vh;
  margin-top: 5vh;
}

.Teamcontainer {
  position: relative;
  top: -2vh;
}

.imgTeam {
  position: relative;
  top: -11vh;
  left: 0vh;
  width: 5vw;
  height: 5vw;
  margin-left: 15px;
  margin-top: 15px;
}

.textTeam {
  flex-shrink: 0;
  color: #000;
  font-family: Microsoft YaHei;
  font-size: 15px;
  font-style: normal;
  font-weight: 700;
  line-height: normal;
  position: relative;
  top: -11vh;
  left: 2vh;
  position: center;
  white-space: nowrap;
  /* 不换行 */
  overflow: hidden;
  /* 隐藏超出容器宽度的文本 */
  text-overflow: ellipsis;
  /* 使用省略号表示超出的文本 */
}

.shooter {
  position: relative;
  top: -12vh;
  left: -3.5vw;
  width: 12vw;
}

.imgForVideo {
  background-image: url("https://img2.baidu.com/it/u=3484181004,3499441771&fm=253&fmt=auto&app=138&f=JPEG?w=889&h=500");
  /* 指定背景图片为一个播放图标 */
  background-size: cover;
  /* 让背景图片适应伪元素的大小 */
}

/* <--<--联赛样式结束-->--> */
</style>