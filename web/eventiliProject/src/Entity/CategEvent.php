<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\CategEventRepository; 
use Symfony\Component\Validator\Constraints as Assert;

#[ORM\Entity(repositoryClass:CategEventRepository::class)]
class CategEvent
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_categ')]
    private ?int $idCateg=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le type')]
    #[Assert\NotNull(message: 'Merci de remplir le type')]
    #[Assert\Length(
        // min: 1,
        max: 30,
        // minMessage: 'Your first name must be at least {{ limit }} characters long',
        maxMessage: 'le type ne doit pas dépasser {{ limit }} charactères',
    )]
    private ?String $type=null;
//---------------------------------------------------------------------------------------

    public function getIdCateg(): ?int
    {
        return $this->idCateg;
    }

    public function getType(): ?string
    {
        return $this->type;
    }

    public function setType(string $type): self
    {
        $this->type = $type;

        return $this;
    }


}
