<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Chat
 *
 * @ORM\Table(name="chat", indexes={@ORM\Index(name="fk_chat", columns={"userId"}), @ORM\Index(name="fk_eventchat", columns={"event_id"})})
 * @ORM\Entity
 */
class Chat
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_chat", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idChat;

    /**
     * @var string
     *
     * @ORM\Column(name="message_chat", type="text", length=65535, nullable=false)
     */
    private $messageChat;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="dataheure_chat", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $dataheureChat = 'CURRENT_TIMESTAMP';

    /**
     * @var \Personne
     *
     * @ORM\ManyToOne(targetEntity="Personne")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="userId", referencedColumnName="id_pers")
     * })
     */
    private $userid;

    /**
     * @var \Evenement
     *
     * @ORM\ManyToOne(targetEntity="Evenement")
     * @ORM\JoinColumns({
     *   @ORM\JoinColumn(name="event_id", referencedColumnName="id_ev")
     * })
     */
    private $event;

    public function getIdChat(): ?int
    {
        return $this->idChat;
    }

    public function getMessageChat(): ?string
    {
        return $this->messageChat;
    }

    public function setMessageChat(string $messageChat): self
    {
        $this->messageChat = $messageChat;

        return $this;
    }

    public function getDataheureChat(): ?\DateTimeInterface
    {
        return $this->dataheureChat;
    }

    public function setDataheureChat(\DateTimeInterface $dataheureChat): self
    {
        $this->dataheureChat = $dataheureChat;

        return $this;
    }

    public function getUserid(): ?Personne
    {
        return $this->userid;
    }

    public function setUserid(?Personne $userid): self
    {
        $this->userid = $userid;

        return $this;
    }

    public function getEvent(): ?Evenement
    {
        return $this->event;
    }

    public function setEvent(?Evenement $event): self
    {
        $this->event = $event;

        return $this;
    }


}
