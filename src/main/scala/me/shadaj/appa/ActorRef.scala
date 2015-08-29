package me.shadaj.appa

trait ActorRef {
  def actor: Actor

  def !(msg: Any)(implicit sender: ActorRef) = actor._receive(sender)(msg)
}
