package meta.writer

/**
  * @author z.benrhouma
  * @since 27/07/2015
  */
class CallSequence() {
    var calls : Array[Call] = Array()
    def ++(c : Call ): Unit ={
      calls = calls :+ c
    }
}

