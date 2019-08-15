# rv
`rv` is a custom recyclerView adapter that enables `infinite scroll` by default and `load more` capabilities.

![Demo](https://media.giphy.com/media/m9SYyx6t1E2le1DM0e/giphy.gif)

## Installation

 Add the dependency : 
```gradle
dependencies {
    implementation 'com.random-guys:rv:0.0.1'
}
```

## Usage

```kotlin
class MainActivity : AppCompatActivity(), LoadMoreListener {

    private val faker = Faker()
    private var theNames = ArrayList<String>()
    private lateinit var namesAdapter: NamesAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var recyclerScrollMoreListener: RecyclerScrollMoreListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        namesAdapter = NamesAdapter(this)
        namesAdapter.loadMoreListener = this

        for (i in 0 until 1000) theNames.add(faker.name().nameWithMiddle())

        namesAdapter.add(theNames.subList(0, 20))

        linearLayoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = namesAdapter

        recyclerScrollMoreListener = RecyclerScrollMoreListener(linearLayoutManager, namesAdapter)
        recyclerView.addOnScrollListener(recyclerScrollMoreListener)
    }

    override fun onLoadMore(page: Int, total: Int) {
        if (theNames.size >= total) {
            namesAdapter.add(theNames.subList(page * 10, (page * 10) + 10))
            recyclerView.post { namesAdapter.notifyItemInserted(namesAdapter.itemCount) }
        }
    }
}
```

## Contribution
The `BaseViewHolder` class can be extended to support multiple view types.

```
Copyright (c) 2019 Random Guys

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
