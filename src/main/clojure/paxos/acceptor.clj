(ns paxos.acceptor)

(def current-proposal-number 0)
(def current-proposal-value nil)

(defn reset-acceptor []
  (def current-proposal-number 0)
  (def current-proposal-value nil))

(defn proposal-inform [proposal-number proposal-value]
  (list proposal-number proposal-value))

(defn proposal-promise [proposal-number proposal-value]
  (def current-proposal-number proposal-number)
  (proposal-inform proposal-number proposal-value))

(defn proposal-accept [proposal-number proposal-value]
  (def current-proposal-number proposal-number)
  (def current-proposal-value proposal-value)
  (proposal-inform proposal-number proposal-value))

(defn prepare-request [proposal-number]
  (if (> proposal-number current-proposal-number)
    (proposal-promise proposal-number current-proposal-value)
    (proposal-inform current-proposal-number current-proposal-value)))

(defn accept-request [proposal-number proposal-value]
  (if (>= proposal-number current-proposal-number)
    (proposal-accept proposal-number proposal-value)
    (proposal-inform current-proposal-number current-proposal-value)))
