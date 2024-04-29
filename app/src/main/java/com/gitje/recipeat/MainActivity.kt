package com.gitje.recipeat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gitje.recipeat.models.Recipe
import com.gitje.recipeat.ui.theme.RecipeatTheme
import com.gitje.recipeat.util.fontSizeDimensionResource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content(getGeneralRecipes(), getDessertRecipes(), getBakingRecipes())
        }
    }
}

@Composable
fun RecipeCard(
    name: String,
    image: String
) {
    Surface(
        modifier = Modifier
            .size(dimensionResource(id = com.intuit.sdp.R.dimen._120sdp))
            .padding(
                start = dimensionResource(id = R.dimen.padding_small),
                end = dimensionResource(id = R.dimen.padding_small)
            ),
        shadowElevation = dimensionResource(id = com.intuit.sdp.R.dimen._5sdp),
        shape = RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._15sdp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = dimensionResource(id = R.dimen.padding_small)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )

            Text(
                text = name,
                fontSize = fontSizeDimensionResource(id = com.intuit.ssp.R.dimen._12ssp),
                modifier = Modifier.padding(top = dimensionResource(id = com.intuit.sdp.R.dimen._5sdp))
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(cardItems: List<Recipe>, dessertRecipes: List<Recipe>, bakingRecipes: List<Recipe>) {
    RecipeatTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Recipe overview")
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
                )
            }
        ) {
            val topPadding =
                it.calculateTopPadding() + dimensionResource(id = com.intuit.sdp.R.dimen._10sdp)
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(top = topPadding)
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                RecipeCollection(name = "General recipes", recipes = cardItems)
                RecipeCollection(name = "Desserts", recipes = dessertRecipes)
                RecipeCollection(name = "Baking", recipes = bakingRecipes)
            }
        }
    }
}

@Composable
fun RecipeCollection(name: String, recipes: List<Recipe>) {
    val listState = rememberLazyListState()

    val frontArrowVisible = remember(listState.isScrollInProgress) {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0
        }
    }

    val rearArrowVisible = remember(listState.isScrollInProgress) {
        derivedStateOf {
            (listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0) < recipes.size-1
        }
    }

    Column(
        Modifier.padding(
            start = dimensionResource(id = com.intuit.sdp.R.dimen._10sdp),
            end = dimensionResource(id = com.intuit.sdp.R.dimen._10sdp),
            bottom = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp),
            top = 0.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = com.intuit.sdp.R.dimen._5sdp)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = name, modifier = Modifier.padding(10.dp))
            IconButton(
                onClick = { /*Add new item*/ },
                colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_add),
                    contentDescription = "Add"
                )
            }
        }

        Box {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)),
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.tertiaryContainer
                            )
                        ), RoundedCornerShape(dimensionResource(id = com.intuit.sdp.R.dimen._20sdp))
                    )
                    .padding(vertical = dimensionResource(id = R.dimen.padding_med), horizontal = dimensionResource(id = com.intuit.sdp.R.dimen._20sdp)),
                state = listState
            ) {
                items(items = recipes, key = { item -> item.id }) {
                    RecipeCard(name = it.name, image = "")
                }
            }

            if (frontArrowVisible.value)
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .rotate(180f),
                    contentDescription = null
                )

            if (rearArrowVisible.value)
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_next),
                    modifier = Modifier.align(Alignment.CenterEnd),
                    contentDescription = null
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    RecipeatTheme {
        RecipeCard(
            "testRecipe",
            ""
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BigPreview() {
    Content(getGeneralRecipes(), getDessertRecipes(), getBakingRecipes())
}

fun getGeneralRecipes(): List<Recipe> {
    val cardItems = mutableListOf<Recipe>()
    cardItems.add(Recipe().apply {
        this.id = 0
        this.name = "General 1"
    })
    cardItems.add(Recipe().apply {
        this.id = 1
        this.name = "General 2"
    })
    cardItems.add(Recipe().apply {
        this.id = 2
        this.name = "General 3"
    })
    cardItems.add(Recipe().apply {
        this.id = 3
        this.name = "General 4"
    })
    return cardItems
}

fun getDessertRecipes(): List<Recipe> {
    val cardItems = mutableListOf<Recipe>()
    cardItems.add(Recipe().apply {
        this.id = 0
        this.name = "Dessert 1"
    })
    cardItems.add(Recipe().apply {
        this.id = 1
        this.name = "Dessert 2"
    })
    cardItems.add(Recipe().apply {
        this.id = 2
        this.name = "Dessert 3"
    })
    cardItems.add(Recipe().apply {
        this.id = 3
        this.name = "Dessert 4"
    })
    return cardItems
}

fun getBakingRecipes(): List<Recipe> {
    val cardItems = mutableListOf<Recipe>()
    cardItems.add(Recipe().apply {
        this.id = 0
        this.name = "Baking 1"
    })
    cardItems.add(Recipe().apply {
        this.id = 1
        this.name = "Baking 2"
    })
    cardItems.add(Recipe().apply {
        this.id = 2
        this.name = "Baking 3"
    })
    cardItems.add(Recipe().apply {
        this.id = 3
        this.name = "Baking 4"
    })
    return cardItems
}
