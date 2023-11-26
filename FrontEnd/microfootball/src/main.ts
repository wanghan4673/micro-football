import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import piniaPluginPersistedstate from 'pinia-plugin-persistedstate'

import { TUIComponents, TUICore, genTestUserSig } from './TUIKit';
// import TUICallKit
import { TUICallKit } from '@tencentcloud/call-uikit-vue';

const SDKAppID = 1600013711; // Your SDKAppID
const secretKey = '8a1959745a1485707b6fa66ef37a20ccca6ad6e360834fc0b0a3c9c9efd916df'; //Your secretKey
const userID = 'administrator'; // User ID


// // init TUIKit
// const TUIKit = TUICore.init({
//   SDKAppID,
// });
// // TUIKit add TUIComponents
// TUIKit.use(TUIComponents);
// // TUIKit add TUICallKit
// TUIKit.use(TUICallKit);


// // login TUIKit
// TUIKit.login({
//   userID: userID,
//   userSig: genTestUserSig({
//     SDKAppID,
//     secretKey,
//     userID,
//   }).userSig, // The password with which the user logs in to IM. It is the ciphertext generated by encrypting information such as userID.For the detailed generation method, see Generating UserSig
// });


const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);
app.use(router).use(ElementPlus).use(pinia)
// app.use(TUIKit)
app.mount('#app')