# android 拍照 选择图片 裁剪

> 权限需要自行处理

## 使用

1. 添加依赖

   **Step 1.** Add the JitPack repository to your build file

   Add it in your root build.gradle at the end of repositories:

   ```groovy
   	allprojects {
   		repositories {
   			...
   			maven { url 'https://jitpack.io' }
   		}
   	}
   ```

   **Step 2.** Add the dependency

   ```groovy
   	dependencies {
   	        implementation 'com.github.Mrqinlei:TokePhotoUtil:v1.0'
   	}
   ```

2. 处理 6.0 权限问题

   > 需自行处理

3. 代码中使用

   ```java
   //拍照获取图片
   TokePhotoUtils.getInstance().captureCamera(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   });
   //拍照并裁剪为正方形图片
   TokePhotoUtils.getInstance().captureCameraForSquare(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   });
   //拍照并设置裁剪比例
   TokePhotoUtils.getInstance().captureCameraForCrop(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   }, 2, 1);
   //选择图库图片
   TokePhotoUtils.getInstance().captureGallery(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   });
   //选择图库并裁剪为正方形图片
   TokePhotoUtils.getInstance().captureGalleryForSquare(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   });
   //选择图库并设置裁剪比例
   TokePhotoUtils.getInstance().captureGalleryForCorp(MainActivity.this, new TokePhotoCallBack() {
       @Override
       public void onSuccess(File file) {
           mImageView.setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
       }
   }, 2, 1);
   ```

1. 图片压缩问题处理（可以使用 luban ）

   > 需自行处理