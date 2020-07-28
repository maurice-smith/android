package com.mo.jetpack.hiltdemo.model

import javax.inject.Inject

class NameModel @Inject constructor(val firstName: String, val lastName: String)