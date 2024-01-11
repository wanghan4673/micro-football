<template>
    <div style="display: flex;justify-content: center;">
        <div style="display: flex; flex-direction: column;align-items: flex-start;width: 80vw; ">
            <div style="display: flex;">
                <el-icon @click="back" style="margin-top: 30px; margin-left: 50px;">
                    <Back />
                </el-icon>
                <div class="QC-title" v-if="post.title">
                    <p style="margin-top: 16px; margin-left: 20px;font-size: xx-large; color: rgb(5, 103, 103);">{{
                        post.title
                    }}</p>
                </div>
            </div>

            <div id="QC-bg">
                <div id="Box" v-if="post.content">
                    <div style="display: flex;width: 300px;gap:20px;margin-bottom: 20px;">
                        <p style="font-weight: light; font-size: x-large;">帖主 </p>
                        <p style="font-weight: bold; font-size: x-large;">{{ poster.name }}</p>
                    </div>
                    <!-- <div style="width: 100%;word-wrap: break-word; margin-top: 5px;margin-bottom:15px; font-size: larger; ">
                            <span style="font-weight: bold;">帖主 </span>
                            <el-popover :visible="visible" placement="top" :width="360">
                                <div style="padding: 10px;">
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>用户名：</p>
                                        <p>{{ commentuser.name }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>聊天ID:</p>
                                        <p>{{ commentuser.account }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>个性签名：</p>
                                        <p>{{ commentuser.signature }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>喜欢的联赛：</p>
                                        <p>{{ commentuser.favorite_league }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>关注数：</p>
                                        <p>{{ commentuser.follow }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>粉丝数：</p>
                                        <p>{{ commentuser.fans }}</p>
                                    </div>
                                </div>
                                <div style="text-align: right; margin: 0" v-if="poster.id != store.user.userid">
                                    <el-button size="small" type="primary" @click="clickfollow(poster.id)">
                                        {{ commentuser.isFollowing ? '取消关注' : '关注' }}
                                    </el-button>
                                </div>
                                <template #reference>
                                    <div @click="visible = true"
                                        style="display: flex; align-items: center; cursor: pointer;">
                                        <p @mouseover="loadcommentuser(poster.id)" @mouseleave="visible = false">{{
                                            poster.name }}</p>
                                    </div>
                                </template>
                            </el-popover>
                        </div> -->
                    <!-- </div> -->
                    <div style="width: 100%;word-wrap: break-word;font-size: x-large;">
                        <p v-html="post.content"></p>
                    </div>
                </div>
                <!-- v-if="props.Question.img && props.Question.img.length > 0"  -->
                <div id="Box" v-if="post.img">
                    <div v-for="img of post.img" :key="img">
                        <img :src="img" alt="图片加载失败" style="max-width: 60%;" />
                    </div>
                </div>
                <div style="display: flex; gap:20px;margin:20px;">
                    <el-icon :size="20" :color="isliked ? '#d57eb7' : ''" @click="likeclick">
                        <CircleCheck />
                    </el-icon>
                    <span style="font-size: 20px; margin-top: -4px;">{{ post.likes }}</span>
                    <el-icon :size="20" :color="iscollected ? '#d57eb7' : ''" @click="collectclick">
                        <Star />
                    </el-icon>
                    <span style="font-size: 20px;margin-top: -4px;">{{ post.collect }}</span>

                    <el-popover :visible="visiblereport" placement="bottom" :width="360">
                        <p style="font-size: 16px;padding-left: 5px; margin-bottom: 15px;">举报理由：</p>
                        <el-input v-model="reportreason" placeholder="举报理由" :rows="3" type="textarea" clearable />
                        <div style="text-align: right; margin: 10px">
                            <el-button type="primary" @click="handleSubmitClick">提交举报</el-button>
                        </div>
                        <template #reference>
                            <div @click="visiblereport = true"
                                style="display: flex; align-items: center; cursor: pointer;margin-top: -4px;">
                                <el-icon :size="20">
                                    <WarningFilled />
                                </el-icon>
                                <span style="font-size: 14px; margin-left: 5px;">举报</span>
                            </div>
                        </template>
                    </el-popover>
                </div>
            </div>
            <div id="QC-bg" v-if="comments != []">
                <p style="font-size: large; font-weight: 400; margin: 20px;margin-left: 5px;">评论 {{ comments ?
                    comments.length : 0
                }}
                </p>
                <div id="Box" style="height: auto;padding-top: 20px;" v-for="(comment, index) of comments" :key="comment">
                    <div
                        style="width: 100%; display:flex; justify-content:space-start;gap: 20px; padding-right:10vw; border-bottom: 1px solid #44484a31;">
                        <div style="max-width: 25%; display: flex;">
                            <!-- <img :src="comment.avatar" alt=""> -->

                            <el-popover :visible="visible" placement="top" :width="360">
                                <div style="padding: 10px;">
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>用户名：</p>
                                        <p>{{ commentuser.name }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>聊天ID:</p>
                                        <p>{{ commentuser.account }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>个性签名：</p>
                                        <p>{{ commentuser.signature }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>喜欢的联赛：</p>
                                        <p>{{ commentuser.favorite_league }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>关注数：</p>
                                        <p>{{ commentuser.follow }}</p>
                                    </div>
                                    <div style="display: flex;justify-content: space-between;">
                                        <p>粉丝数：</p>
                                        <p>{{ commentuser.fans }}</p>
                                    </div>
                                </div>
                                <div style="text-align: right; margin: 0" v-if="comment.userid != store.user.userid">
                                    <el-button size="small" type="primary" @click="clickfollow(comment.userid)">
                                        {{ commentuser.isFollowing ? '取消关注' : '关注' }}
                                    </el-button>
                                </div>
                                <template #reference>
                                    <div @click="visible = true"
                                        style="display: flex; align-items: center; cursor: pointer;margin-top: -4px;">
                                        <p @mouseover="loadcommentuser(comment.userid)" @mouseleave="visible = false">{{
                                            comment.name }}</p>
                                    </div>
                                </template>
                            </el-popover>
                        </div>
                        <div style="max-width:70% ; word-wrap: break-word; overflow: auto;">{{ comment.comment }}</div>
                    </div>
                </div>
            </div>
            <div id="newcomment">
                <el-input v-model="newcomment" placeholder="发布评论" style="height: 100%;width: 60%;"></el-input>
                <el-button color="#5a9feacc" @click="commentclick">发布评论</el-button>
            </div>
        </div>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref, watch } from "vue"
import { useRouter, useRoute } from "vue-router"
import axios from "axios"
const router = useRouter()
const route = useRoute()
import { useForumStore } from '../../stores/forum.ts'
import { useGeneralStore } from '../../stores/general.ts'
import { ElMessage } from 'element-plus'
const forumstore = useForumStore()
const back = () => {
    router.back();
}
let store = useGeneralStore()
let postid = route.query.id;
let post = ref({ title: '', content: '', img: null })
let comments = ref([])
let poster = ref(null)
let isliked = ref(false)
let iscollected = ref(false)
let newcomment = ref("")
let reportreason = ref('')
let commentuser = ref({
    name: '',
    account: '',
    signature: '',
    favorite_league: '',
    follow: '',
    fans: '',
    isFollowing: '',
})
// let visible = ref(false);
onMounted(() => {
    getUserProfile()
    loadPost(postid)
})
const loadcommentuser = async (followerid) => {
    console.log(store.user.userid)
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.get("/api/users/user-detail?userid=" + followerid, {
            headers: {
                'token': `${token}`
            }
        });
        if (response.status == 200) {
            console.log(response)
            commentuser.value.name = response.data.data.name
            commentuser.value.account = response.data.data.account
            commentuser.value.signature = response.data.data.signature
            commentuser.value.favorite_league = response.data.data.favoriteLeague
            commentuser.value.follow = response.data.data.follow
            commentuser.value.fans = response.data.data.fans
            commentuser.value.isFollowing = response.data.data.isFollowing
        }
    } catch (error) {
        console.log(error)
    }
}
const clickfollow = async (followerid) => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/users/follow?followerid=" + followerid, {}, {
            headers: {
                'token': `${token}`
            }
        });
        if (response.status == 200) {
            console.log(response)
            if (commentuser.value.isFollowing) {
                commentuser.value.fans -= 1
                commentuser.value.isFollowing = false
            }
            else {
                commentuser.value.fans += 1
                commentuser.value.isFollowing = true
            }
        }
        if (commentuser.value.isFollowing) {
            ElMessage({
                message: "关注成功",
                type: "success",
            })
        } else {
            ElMessage({
                message: "取消关注成功",
                type: "success",
            })
        }
    } catch (error) {
        console.log(error)
    }
}
const loadPost = async (postid) => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.get("/api/forum/posts/" + postid, {
            headers: {
                'token': `${token}`
            }
        });
        if (response.status == 200) {
            console.log(response.data)
            post.value = response.data.data.post
            comments.value = response.data.data.commentInfos
            poster.value = response.data.data.user
            isliked.value = response.data.data.isliked
            iscollected.value = response.data.data.iscollected
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "获取失败，请先登录",
            type: "error"
        })
    }
}

const getUserProfile = async () => {
    const token = localStorage.getItem('token')
    try {
        const response = await axios.get('/api/users/user-info', {
            headers: {
                'token': token,
            }
        })
        if (response.data.code == 1) {
            store.user.username = response.data.data.name
            store.user.league = response.data.data.favoriteLeague
            console.log(response.data.data)
        }
    } catch (error) {
    }
}
const handleSubmitClick = async () => {
    console.log(store.user.username)
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/report?reporterName=" + store.user.username + "&reason=" + reportreason.value + "&postId=" + postid, {
        }, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            ElMessage({
                message: "举报成功",
                type: "success"
            })
            loadPost(postid)
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "举报失败",
            type: "error"
        })
    }
}

const likeclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/posts/like?postid=" + postid, {}, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            if (isliked.value) {
                isliked.value = false;
                post.value.likes--;
            } else {
                isliked.value = true;
                post.value.likes++;
            }
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: isliked ? "取消点赞失败" : "点赞失败",
            type: "error"
        })
    }
}
const collectclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/posts/collect?postid=" + postid, {}, {
            headers: {
                'token': `${token}`,
            }
        });
        if (response.status == 200) {
            if (iscollected.value) {
                iscollected.value = false;
                post.value.collect--;
            } else {
                iscollected.value = true;
                post.value.collect++;
            }
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: iscollected ? "取消收藏失败" : "收藏失败",
            type: "error"
        })
    }
}
const commentclick = async () => {
    let response
    let token = localStorage.getItem('token')
    try {
        response = await axios.post("/api/forum/posts/comment", {
            "postid": postid,
            "comment": newcomment.value,
        },
            {
                headers: {
                    'Content-Type': 'application/json',
                    'token': `${token}`,
                }
            });
        if (response.status == 200) {
            ElMessage({
                message: "评论成功",
                type: "success"
            })
            newcomment.value = ''
            loadPost(postid)
        }
    } catch (error) {
        console.log(error)
        ElMessage({
            message: "评论失败",
            type: "error"
        })
    }
}
// const convertFileToImageUrl = (file) => {
//     if (file) {
//         return "http://localhost:8080/" + file;
//     }
//     return '';
// };
</script>

<style lang="scss" scoped>
#QC-bg {
    width: 90%;
    background-color: #fff;
    // max-width: 1400px;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    margin-top: 10px;
    margin-left: 50px;
    padding-left: 30px;
    margin-bottom: 20px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
    // border: 1px solid #000;
}

#Box {
    width: 100%;
    display: flex;
    flex-direction: column;
    justify-content: flex-start;
    padding: 10px;
    // background-color: #efefef80;
}

.QC-title {
    font-size: large;
    font-weight: bold;
}

#newcomment {
    display: flex;
    justify-content: flex-start;
    width: 90%;
    background-color: #fff;
    max-width: 1200px;
    // box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.1);
    margin-left: 48px;
    // padding-left: 30px;
    margin-bottom: 20px;
}
</style> 
