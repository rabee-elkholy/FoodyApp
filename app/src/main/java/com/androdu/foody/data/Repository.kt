package com.androdu.foody.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    val apiSource: RecipesApiSource,
    val localSource: RecipesLocalSource
)