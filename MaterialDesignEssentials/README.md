#Material Design Essentials

---

This library includes a set of XMLs to help you build apps that perfectly fit the **Google Material Design** specifics.

这个库包含了一整套XML来帮助你建造一个完美符合**谷歌材质化设计规范**的应用。



##Quick Start

###Step 1 - Add Material Design Essentials as a library project
>* Android Studio: `File` > `New` > `Import Module...`
>* Eclipse: `File` > `Import...` > `General` > `Existing Projects into Workspace` > `Browse...` (select `Add project to working sets` if current library hasn't in your workspace) > `Finish`

###Step 2 - Use them in your Projects


```xml
<!--You can simply load styles using[style="@style/STYLE_NAME"]
	or use the dimensions in @dimen-->

<android.support.v7.widget.CardView
	android:id="@+id/cardView2"
    style="@style/MaterialCardView">

    <LinearLayout
    	android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:paddingBottom="@dimen/cards_contents_margin_with_no_additional_contents">

        <ImageView
        	android:layout_width="wrap_content"
            android:layout_height="150dp"
            android:id="@+id/imageView2"
            android:src="@drawable/iphone"
            android:scaleType="centerCrop" />

        <TextView
        	android:text="@string/hello_world"
            style="@style/ContentBlock.TextView.PrimaryTitle" />

	</LinearLayout>
</android.support.v7.widget.CardView>

```

##License


	Copyright (c) 2015 KavelCortex
	
	Licensed under the Apache License, Version 2.0 (the "License");
	you may not use this file except in compliance with the License.
	You may obtain a copy of the License at
	
		http://www.apache.org/licenses/LICENSE-2.0
 	
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.

