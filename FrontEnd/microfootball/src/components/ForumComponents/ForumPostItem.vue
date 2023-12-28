<template>
    <div id='PI-bg'>
        <div id="PostItem">
            <p class="post-title" v-html="stripContentHTML(props.post.title)"></p>
            <el-text line-clamp="4" class="post-contain" v-html = "stripContentHTML(props.post.content)"></el-text>
        </div>
        <!-- <div id="ImageContainer" v-if="props.post.img">
            <img id="PostImg" :src="props.post.img[0]" />
        </div> -->
    </div>
</template>
  
<script setup>
const props = defineProps({
    post: Object,
});

function convertHtmlToText(htmlString) {
    // 创建一个新的DOMParser实例
    const parser = new DOMParser();
    
    // 使用DOMParser解析HTML字符串
    const parsedDocument = parser.parseFromString(htmlString, 'text/html');
    
    // 获取解析后文档的文本内容
    const plainText = parsedDocument.body.textContent || "";
    
    return stripContentHTML(plainText);
}

const stripContentHTML = (html) => {
    const strippedHTML = html.replace(/<(?!\/?font)[^>]*>/g, '');
    // 检查是否有高亮字段
    const match = strippedHTML.match(/<font color='#fc5531'>(.*?)<\/font>/);
    if (match) {
        // 如果有高亮字段，则返回高亮字段及其前后文本
        const start = Math.max(match.index - 20, 0); // 取前20个字符
        const end = Math.max(match.index + match[0].length + 20, strippedHTML.length); // 取后20个字符
        return strippedHTML.substring(start, end);
    } else {
        // 如果没有高亮字段，直接返回整个内容
        return strippedHTML;
    }
}

</script>
  
<style lang="scss" scoped>
#PI-bg {
    width: 90%;
    padding-left: 20px;
    height: 200px;
    display: flex;
    align-items: flex-start;
    background-color: #efefef80;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.2);
    // border-bottom: 1px solid #00000043;
}

#PostItem {
    display: flex;
    flex-direction: column;
    width: auto;
    word-wrap: break-word;
    padding: 10px;
}

.post-title {
    font-size: larger;
    font-style: normal;
    font-weight: 400;
    color: #1599e0;
    margin-bottom: 8px;
}

.post-contain {
    font-size: medium;
    font-style: normal;
    font-weight: 400;
    margin-bottom: 20px;
    width: 100%;
}

#ImageContainer {
    display: flex;
    justify-content: center;
    align-items: center;
    // border: 2px solid #000;
    height: 90%;
    padding: 10px;
}

#PostImg {
    max-width: 100%;
    max-height: 100%;
    // height: auto;
    border: none;
    opacity: 0.8;
    border-radius: 5px;
}
</style>
  