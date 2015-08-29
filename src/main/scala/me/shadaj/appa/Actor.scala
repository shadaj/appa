package me.shadaj.appa

import scala.collection.mutable

trait Actor { selfActor =>
  type Receive = PartialFunction[Any, Unit]

  private var _sender: ActorRef = null
  def sender() = _sender

  def receive: Receive
  val ref = new ActorRef {
    override val actor: Actor = selfActor
  }

  private val receive_stack = mutable.Stack(receive)

  private[appa] def _receive(sender: ActorRef) = {
    _sender = sender
    receive_stack.top
  }

  implicit val self = ref

  object context {
    def become(newReceive: Receive) = {
      receive_stack.push(newReceive)
    }

    def unbecome() = {
      receive_stack.pop()
    }
  }
}
