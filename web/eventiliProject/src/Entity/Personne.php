<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;
use App\Repository\PersonneRepository;
use DateTime;
use phpDocumentor\Reflection\Types\Self_;
use PhpParser\Node\Name;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Symfony\Component\Security\Core\User\UserInterface;
use Symfony\Component\Mime\Message;
use Symfony\Component\Validator\Constraints as Assert;

#[UniqueEntity(fields: ['email'], message: 'Un compte déjà existe avec cet email')]
#[ORM\Entity(repositoryClass: PersonneRepository::class)]
class Personne implements UserInterface
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(name:'id_pers')]
    private ?int $idPers=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le nom')]
    #[Assert\Length(min: 1, max: 255)]
    #[Assert\Regex(
        pattern: "/^[^0-9]*$/",
       message: "Le nom ne doit pas contenir de chiffre"
    )]  
    private ?String $nomPers=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le prénom')]
    #[Assert\Regex(
        pattern: "/^[^0-9]*$/",
       message: "Le prénom ne doit pas contenir de chiffre"
    )]  
    private ?String $prenomPers=null;
   
    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de remplir le numéro')]
    #[Assert\Length(exactMessage: "le numéro doit contenir exactement {{ limit }} chiffres.",
    exactly:  8)]
    #[Assert\Regex(
        pattern: "/^[^a-zA-Z]+$/",
       message: "Le numéro ne doit pas contenir de lettres"
    )]  
    private ?String $numTel=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de saisir un email')]
    #[Assert\Email(message: "L'adresse email '{{ value }}' n'est pas valide.")]
    private ?String $email=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de saisir un mot de passe')]
    #[Assert\Length(min: 8, minMessage:"Le mot de passe doit doit etre supérieur à 8")]
    #[Assert\Regex(
        pattern: '/^(?=.*[a-zA-Z])(?=.*\d).+$/',
        message: "Le mot de passe doit contenir au moins une lettre et un chiffre."
    )]
    private ?String $mdp=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de saisir une adresse')]
    private ?String $adresse=null;

    #[ORM\Column]
    #[Assert\NotBlank(message: 'Merci de saisir un rib')]
    #[Assert\Length(exactMessage: "le rib doit contenir exactement {{ limit }} chiffres.",
    exactly:  20)]
    #[Assert\Regex(
        pattern: "/^[^a-zA-Z]+$/",
       message: "Le rib ne doit pas contenir de lettres"
    )] 
    private ?String $rib=null;

    #[ORM\Column]
    private ?String $role=null;

    #[ORM\Column]
    private ?String $token="";

    #[ORM\Column]
    private ?int $is_verified=0;

    #[ORM\Column]
    private ?DateTime $date;

    public function __construct()
    {
        $this->date = new DateTime();
    }
    public function getIs_verified(): ?int
    {
        return $this->is_verified;
    }
    public function getDate(): ?DateTime
    {
        return $this->date;
    
    }public function setDate(DateTime $date): self
    {
        $this->date = $date;

        return $this;
    }
    public function setIs_verified(int $is_verified): self
    {
        $this->is_verified = $is_verified;

        return $this;
    }
    public function getIdPers(): ?int
    {
        return $this->idPers;
    }
    public function getNomPers(): ?string
    {
        return $this->nomPers;
    }

    public function setNomPers(string $nomPers): self
    {
        $this->nomPers = $nomPers;

        return $this;
    }

    public function getPrenomPers(): ?string
    {
        return $this->prenomPers;
    }

    public function setPrenomPers(string $prenomPers): self
    {
        $this->prenomPers = $prenomPers;

        return $this;
    }

    public function getNumTel(): ?string
    {
        return $this->numTel;
    }

    public function setNumTel(string $numTel): self
    {
        $this->numTel = $numTel;

        return $this;
    }

    public function getEmail(): ?string
    {
        return $this->email;
    }

    public function setEmail(string $email): self
    {
        $this->email = $email;

        return $this;
    }

    public function getMdp(): ?string
    {
        return $this->mdp;
    }

    public function setMdp(string $mdp): self
    {
        $this->mdp = $mdp;

        return $this;
    }

    public function getAdresse(): ?string
    {
        return $this->adresse;
    }

    public function setAdresse(string $adresse): self
    {
        $this->adresse = $adresse;

        return $this;
    }

    public function getRib(): ?string
    {
        return $this->rib;
    }

    public function setRib(string $rib): self
    {
        $this->rib = $rib;

        return $this;
    }

    public function getRole(): ?string
    {
        return $this->role;
    }

    public function setRole(string $role): self
    {
        $this->role = $role;

        return $this;
    }

    public function getToken(): ?string
    {
        return $this->token;
    }

    public function setToken(string $token): self
    {
        $this->token = $token;

        return $this;
    }
    public function __toString()
    {
        return (string) $this->idPers;
    }
   
    public function getUserIdentifier(): string
    {
        return $this->email; // Remplacez avec le champ qui représente l'identifiant unique de l'utilisateur
    }

    public function getRoles(): array
    {
        return ['ROLE_USER'];
        // Retournez un tableau de rôles auxquels l'utilisateur appartient
        // Par exemple : return ['ROLE_USER'];
    }

    public function getPassword(): string
    {
        return $this->mdp; // Remplacez avec le champ qui représente le mot de passe de l'utilisateur
    }

    public function getSalt(): ?string
    {
        // Vous pouvez générer et retourner une valeur de sel aléatoire ici
        // ou retourner null si vous n'utilisez pas de sel
        return null;
    }

    public function eraseCredentials()
    {
        // Vous pouvez implémenter cette méthode pour effacer toute donnée sensible de l'utilisateur,
        // par exemple, le mot de passe en clair après l'authentification
    }

    public function getUsername(): string
    {
        return $this->nomPers; // Remplacez avec le champ qui représente le nom d'utilisateur de l'utilisateur
    }
}



