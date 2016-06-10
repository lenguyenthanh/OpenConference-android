package com.openconference.sessions

import android.content.Context
import com.openconference.R
import com.openconference.dagger.ApplicationContext
import com.openconference.myschedule.MyScheduleScope
import com.openconference.myschedule.presentationmodel.MySchedulePresentationModelTransformer
import com.openconference.sessions.presentationmodel.PhoneSessionPresentationModelTransformer
import com.openconference.sessions.presentationmodel.SessionPresentationModelTransformer
import dagger.Module
import dagger.Provides

/**
 * @author Hannes Dorfmann
 */
@Module
class MyScheduleModule() {

  @Provides
  @MyScheduleScope
  fun providesMySchedulePresentationTransformer() = MySchedulePresentationModelTransformer()

  @Provides
  @MyScheduleScope
  fun provideSessionPresentationModelTransformer(
      @ApplicationContext context: Context): SessionPresentationModelTransformer =
      PhoneSessionPresentationModelTransformer(context.getString(
          R.string.sessions_sticky_date_format)) // TODO make that changeable at runtime as any other config change
}