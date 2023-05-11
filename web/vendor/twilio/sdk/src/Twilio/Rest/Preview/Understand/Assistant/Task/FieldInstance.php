<?php

/**
 * This code was generated by
 * ___ _ _ _ _ _    _ ____    ____ ____ _    ____ ____ _  _ ____ ____ ____ ___ __   __
 *  |  | | | | |    | |  | __ |  | |__| | __ | __ |___ |\ | |___ |__/ |__|  | |  | |__/
 *  |  |_|_| | |___ | |__|    |__| |  | |    |__] |___ | \| |___ |  \ |  |  | |__| |  \
 *
 * Twilio - Preview
 * This is the public Twilio REST API.
 *
 * NOTE: This class is auto generated by OpenAPI Generator.
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */


namespace Twilio\Rest\Preview\Understand\Assistant\Task;

use Twilio\Exceptions\TwilioException;
use Twilio\InstanceResource;
use Twilio\Values;
use Twilio\Version;
use Twilio\Deserialize;


/**
 * @property string|null $accountSid
 * @property \DateTime|null $dateCreated
 * @property \DateTime|null $dateUpdated
 * @property string|null $fieldType
 * @property string|null $taskSid
 * @property string|null $assistantSid
 * @property string|null $sid
 * @property string|null $uniqueName
 * @property string|null $url
 */
class FieldInstance extends InstanceResource
{
    /**
     * Initialize the FieldInstance
     *
     * @param Version $version Version that contains the resource
     * @param mixed[] $payload The response payload
     * @param string $assistantSid The unique ID of the parent Assistant.
     * @param string $taskSid The unique ID of the Task associated with this Field.
     * @param string $sid A 34 character string that uniquely identifies this resource.
     */
    public function __construct(Version $version, array $payload, string $assistantSid, string $taskSid, string $sid = null)
    {
        parent::__construct($version);

        // Marshaled Properties
        $this->properties = [
            'accountSid' => Values::array_get($payload, 'account_sid'),
            'dateCreated' => Deserialize::dateTime(Values::array_get($payload, 'date_created')),
            'dateUpdated' => Deserialize::dateTime(Values::array_get($payload, 'date_updated')),
            'fieldType' => Values::array_get($payload, 'field_type'),
            'taskSid' => Values::array_get($payload, 'task_sid'),
            'assistantSid' => Values::array_get($payload, 'assistant_sid'),
            'sid' => Values::array_get($payload, 'sid'),
            'uniqueName' => Values::array_get($payload, 'unique_name'),
            'url' => Values::array_get($payload, 'url'),
        ];

        $this->solution = ['assistantSid' => $assistantSid, 'taskSid' => $taskSid, 'sid' => $sid ?: $this->properties['sid'], ];
    }

    /**
     * Generate an instance context for the instance, the context is capable of
     * performing various actions.  All instance actions are proxied to the context
     *
     * @return FieldContext Context for this FieldInstance
     */
    protected function proxy(): FieldContext
    {
        if (!$this->context) {
            $this->context = new FieldContext(
                $this->version,
                $this->solution['assistantSid'],
                $this->solution['taskSid'],
                $this->solution['sid']
            );
        }

        return $this->context;
    }

    /**
     * Delete the FieldInstance
     *
     * @return bool True if delete succeeds, false otherwise
     * @throws TwilioException When an HTTP error occurs.
     */
    public function delete(): bool
    {

        return $this->proxy()->delete();
    }

    /**
     * Fetch the FieldInstance
     *
     * @return FieldInstance Fetched FieldInstance
     * @throws TwilioException When an HTTP error occurs.
     */
    public function fetch(): FieldInstance
    {

        return $this->proxy()->fetch();
    }

    /**
     * Magic getter to access properties
     *
     * @param string $name Property to access
     * @return mixed The requested property
     * @throws TwilioException For unknown properties
     */
    public function __get(string $name)
    {
        if (\array_key_exists($name, $this->properties)) {
            return $this->properties[$name];
        }

        if (\property_exists($this, '_' . $name)) {
            $method = 'get' . \ucfirst($name);
            return $this->$method();
        }

        throw new TwilioException('Unknown property: ' . $name);
    }

    /**
     * Provide a friendly representation
     *
     * @return string Machine friendly representation
     */
    public function __toString(): string
    {
        $context = [];
        foreach ($this->solution as $key => $value) {
            $context[] = "$key=$value";
        }
        return '[Twilio.Preview.Understand.FieldInstance ' . \implode(' ', $context) . ']';
    }
}
