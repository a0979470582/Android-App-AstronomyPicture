# AstronomyPicture

這是瀏覽宇宙圖片的 APP

流程：
1. 第一頁按下 request Button 後從以下 Api 抓取 Json 檔，其中每一個項目都包含名稱、描述、大小圖的 URL
2. 第二頁以GridLayoutManager呈現所有小圖，點擊任一小圖進入第三頁
3. 第三頁顯示點擊項目的詳細資訊和大圖

Api 來源: https://raw.githubusercontent.com/cmmobile/NasaDataSet/main/apod.json

工具：Kotlin 語言、Jetpack 套件、MVVM 架構、Repository 架構

樣品：

<table>
  <tr>
    <td>
      <image src="https://user-images.githubusercontent.com/45554149/137345660-d845bde8-ad7f-4952-b3d1-0300e61dd519.jpg" width="200px"/>
    </td>
    <td>
      <image src="https://user-images.githubusercontent.com/45554149/137345662-882d3d04-c1e4-421d-878b-72a13d248006.jpg" width="200px"/>
    </td>
  </tr>
  <tr>
    <td>
      <image src="https://user-images.githubusercontent.com/45554149/137345657-d98b352e-7c5c-4195-aea2-6d65716b5340.jpg" width="200px"/>
    </td>
    <td>
      <image src="https://user-images.githubusercontent.com/45554149/137345644-a1238321-d26b-430d-a87b-6599892ffe67.jpg" width="200px"/>
    </td>
  </tr>
</table>
