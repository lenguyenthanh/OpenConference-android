package com.openconference.sessiondetails

import com.openconference.model.SessionsLoader
import com.openconference.model.errormessage.ErrorMessageDeterminer
import com.openconference.sessiondetails.presentationmodel.SessionDetailsPresentationModelTransformer
import com.openconference.util.RxPresenter
import com.openconference.util.SchedulerTransformer
import javax.inject.Inject

/**
 *
 *
 * @author Hannes Dorfmann
 */
class SessionDetailsPresenter @Inject constructor(scheduler: SchedulerTransformer,
    private val sessionsLoader: SessionsLoader,
    private val presentationModelTransformer: SessionDetailsPresentationModelTransformer,
    errorMessageDeterminer: ErrorMessageDeterminer) : RxPresenter<SessionDetailsView>(
    scheduler, errorMessageDeterminer) {


  fun loadSession(sessionId: String) {
    view?.showLoading()
    subscribe(
        sessionsLoader.getSession(sessionId).map { presentationModelTransformer.transform(it) },
        {
          view?.showContent(it)
        },
        {
          view?.showError(errorMessageDeterminer.getErrorMessageRes(it))
        })
  }

  fun addSessionToSchedule(sessionId: String) {

    schedulerTransformer.schedule(sessionsLoader.addSessionToSchedule(sessionId)).subscribe ({
      view?.showSessionAddedToSchedule()
    }, {
      view?.showErrorWhileAddingSessionToSchedule()
    }
    )


  }

  fun removeSessionFromSchedule(sessionId: String) {
    schedulerTransformer.schedule(sessionsLoader.removeSessionFromSchedule(sessionId)).subscribe ({
      view?.showSessionRemovedFromSchedule()
    }, {
      view?.showErrorWhileRemovingSessionFromSchedule()
    }
    )
  }

}