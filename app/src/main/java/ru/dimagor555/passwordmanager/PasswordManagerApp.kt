package ru.dimagor555.passwordmanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PasswordManagerApp : Application()
/*
    TODO: Here I'm gonna to place general comments and misunderstandings,
          which I faced during the review:
          1. To much modules for this kind of project - I don't understand,
             why did you decide to create so much modules? It looks like as if you found yourself
             confused, when you was working with *password_feature*.usecase module. It name uniquely identifies
             what this module is responsible of, but we have _repository_ package here. This interface should be placed in
             *domain* module. So, there are two reason why it where it is:
              - you don't understand clean architecture completely
              - you gave up with module dependencies and decided to place this package there.
          2. constants module - why it exists? Looks like you forgot to remove it.
          3. ignoring brackets - I'm realize that kotlin allow us to skip it in most cases, but java do it as well. So,
             JCC (Java Code Convention) is applicable here too and if/else construction is requires it. The second reason is
             very hard to review code without them in Git.
 */